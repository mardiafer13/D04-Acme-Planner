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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.entities.controlCheck.Rocke;
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

		request.unbind(entity, model, "author", "text", "info", "rocke.deadline", "rocke.insignia", "rocke.budget", "rocke.important");//cambiar adjunto y tmb el view
	}

	@Override
	public Shout instantiate(final Request<Shout> request) {
		assert request != null;
		
		
		Shout result;
		Date moment;
		Rocke control;
		
		moment = new Date(System.currentTimeMillis() - 1);

		control = new Rocke();
		result = new Shout();
		
		//control.setDeadline(moment);
		
		result.setMoment(moment);
		result.setRocke(control);

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
        

        if(!errors.hasErrors("rocke.deadline")){
        	final Date moment = new Date(System.currentTimeMillis() + 604800000);
        	if(entity.getRocke().getDeadline().compareTo(moment) <= 0) {
        		errors.state(request, false, "rocke.deadline","anonymous.message.form.error.deadline");
        	}
        }
        

		if(!errors.hasErrors("rocke.insignia")){
			//Check if date is current
			//Parse date from form to LocalDate
			
			//INFODATE IS LOCALDATE
			//final LocalDate sheetDate = entity.getSheet().getInfoDate();
			
			//INFODATE IS STRING
//			final String controlDateString = entity.getRocke().getInsignia();
//			final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//            final LocalDate controlDate = LocalDate.parse(controlDateString, dtf);
//            
//            //Get current date as LocalDate
//			final LocalDate today = LocalDate.now();
//			
//			errors.state(request, controlDate.isEqual(today), "rocke.insignia", "anonymous.shout.control.date.currentDate");
//			
//			//Check if date is unique
//	        final Integer sameDate = this.repository.totalControlDates(entity.getRocke().getInsignia());
//	        final Boolean date = sameDate<1;
//			
//			errors.state(request, date, "rocke.insignia", "anonymous.message.form.error.dateControl");
			String [] moment;
			moment = LocalDate.now().toString().split("-");
			final String p = entity.getRocke().getInsignia();
			final Boolean b= p.matches("^\\w{6}:"+moment[0].substring(2)+moment[1]+":"+moment[2]+"$");
			errors.state(request, b, "rocke.insignia", "anonymous.message.error.insignia.xxxx");
			
			
			
			final Integer sameDate = this.repository.totalControlDates(entity.getRocke().getInsignia());
	        final Boolean date = sameDate<1;
			errors.state(request, date, "rocke.insignia", "anonymous.message.form.error.dateControl");
			
//			final Shout existing = this.repository.findOneShoutByKeylem(entity.getRocke().getInsignia());
//			errors.state(request, existing==null, "mailams.keylem", "anonymous.shout.error.xxx.x1.repeat");
		}
        
        
		
        if(entity.getRocke().getBudget() != null) {
        	if(entity.getRocke().getBudget().getAmount() < 0) {
        		errors.state(request, false, "rocke.budget", "anonymous.message.form.error.amountControl");
        	}
        	if(!(entity.getRocke().getBudget().getCurrency().equals("EUR")) && !(entity.getRocke().getBudget().getCurrency().equals("USD")) && !(entity.getRocke().getBudget().getCurrency().equals("GBP"))) {
        		errors.state(request, false, "rocke.budget", "anonymous.message.form.error.currentControl");
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

		moment = new Date(System.currentTimeMillis() - 604800000);
		entity.setMoment(moment);
		this.repository.save(entity);
	}

}