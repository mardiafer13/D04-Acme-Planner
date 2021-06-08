/*
 * AnonymousShoutCreateService.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.anonymous.shout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.entities.controlCheck.ControlCheck;
import acme.entities.shouts.Shout;
import acme.features.administrator.Configuration.AdministratorConfigurationRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractCreateService;

@Service
public class AnonymousShoutCreateService implements AbstractCreateService<Anonymous, Shout> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnonymousShoutRepository			repository;

	@Autowired
	protected AdministratorConfigurationRepository	repositorySpamwords;

	// AbstractCreateService<Administrator, Shout> interface --------------

	@Override
	public boolean authorise(final Request<Shout> request) {
		assert request != null;

		return true;

	}

	@Override
	public void bind(final Request<Shout> request, final Shout entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Shout> request, final Shout entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "author", "text", "info", "control.date", "control.money", "control.isCheck");//cambiar adjunto y tmb el view
	}

	@Override
	public Shout instantiate(final Request<Shout> request) {
		assert request != null;
		
		
		Shout result;
		Date moment;
		ControlCheck control;
		
		moment = new Date(System.currentTimeMillis() - 1);

		control = new ControlCheck();
		result = new Shout();
		
		control.setMomento(moment);
		
		result.setMoment(moment);
		result.setControl(control);

		return result;
	}


	@Override
    public void validate(final Request<Shout> request, final Shout entity, final Errors errors) {
        assert request != null;
        assert entity != null;
        assert errors != null;

        final List<Configuration> sp = this.repository.findManySpamWord();
        final List<Configuration> lsp = new ArrayList<>();
        lsp.addAll(sp);
        
        for (int i = 0; i < lsp.size(); i++) {
            if(lsp.get(i).isSpam(entity.getText())){
                errors.state(request, false, "text", "anonymous.message.form.error.spam");
            }
            if(lsp.get(i).isSpam(entity.getAuthor())) {
            	 errors.state(request, false, "author", "anonymous.message.form.error.spam.author");
            }
        }
        


		if(!errors.hasErrors("control.date")){
			//Check if date is current
			//Parse date from form to LocalDate
			
			//INFODATE IS LOCALDATE
			//final LocalDate sheetDate = entity.getSheet().getInfoDate();
			
			//INFODATE IS STRING
			final String controlDateString = entity.getControl().getDate();
			final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            final LocalDate controlDate = LocalDate.parse(controlDateString, dtf);
            
            //Get current date as LocalDate
			final LocalDate today = LocalDate.now();
			
			errors.state(request, controlDate.isEqual(today), "control.date", "anonymous.shout.control.date.currentDate");
			
			//Check if date is unique
	        final Integer sameDate = this.repository.totalControlDates(entity.getControl().getDate());
	        final Boolean date = sameDate<1;
			
			errors.state(request, date, "control.date", "anonymous.message.form.error.dateControl");
		}
        
        
		
        if(entity.getControl().getMoney() != null) {
        	if(entity.getControl().getMoney().getAmount() < 0) {
        		errors.state(request, false, "control.money", "anonymous.message.form.error.amountControl");
        	}
        	if(!(entity.getControl().getMoney().getCurrency().equals("EUR")) && !(entity.getControl().getMoney().getCurrency().equals("USD"))) {
        		errors.state(request, false, "control.money", "anonymous.message.form.error.currentControl");
        	}
        }
        
        
	}
	
	
	@Override
	public void create(final Request<Shout> request, final Shout entity) {
		assert request != null;
		assert entity != null;
		
//		final boolean confirmation = request.getModel().getBoolean("isCheck");	
//		
//		entity.getControl().setIsCheck(confirmation);
		
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.getControl().setMomento(moment);
		entity.setMoment(moment);
		this.repository.save(entity);
	}

}