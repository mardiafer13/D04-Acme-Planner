/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.dashboard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.forms.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorDashboardRepository repository;
	
	// AbstractShowService<Administrator, Dashboard> interface ----------------


	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, //
			"averageNumberOfJobsPerEmployer", "averageNumberOfApplicationsPerWorker", // 
			"avegageNumberOfApplicationsPerEmployer", "ratioOfPendingApplications", //
			"ratioOfRejectedApplications", "ratioOfAcceptedApplications", "numberPublicTask", "numberPrivateTask", "numberFinalTask", "numberNoFinalTask", "averageDurationPeriodTasks", "deviationDurationPeriodTasks", "minimumDurationPeriodTasks",
			"maximumDurationPeriodTasks", "averageWorkloadTasks", "deviationWorkloadTasks", "minimumWorkloadTasks", "maximumWorkloadTasks", 
			"numberOfCheckTrue", "ratioCheckTrue","ratioCheckFalse", "averageMoneyCurrent1", "averageMoneyCurrent2",
			"deviationMoneyCurrent1", "deviationMoneyCurrent2");
	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		assert request != null;

		Dashboard result;
		Double averageNumberOfApplicationsPerEmployer;
		Double averageNumberOfApplicationsPerWorker;
		Double averageNumberOfJobsPerEmployer;
		Double ratioOfPendingApplications;
		Double ratioOfAcceptedApplications;
		Double ratioOfRejectedApplications;

		final Double numberPublicTask;
		final Double numberPrivateTask;
		final Date now = new Date();
		final List<Task> terminadas = new ArrayList<>();
		final Collection<Task> tasks = this.repository.findTasks();
		Double averageDurationPeriodTasks;
		Double deviationDurationPeriodTasks;
		Double minimumDurationPeriodTasks;
		Double maximumDurationPeriodTasks;

		Double averageWorkloadTasks;
		Double deviationWorkloadTasks;
		Double minimumWorkloadTasks;
		Double maximumWorkloadTasks;

		//Parte examen
		//final Collection<Shout> shouts = this.repository.findShouts();
		Double numberOfCheckTrue;
		Double ratioCheckTrue;
		Double ratioCheckFalse;
		Double averageMoneyCurrent1;
		Double averageMoneyCurrent2;
		Double deviationMoneyCurrent1;
		Double deviationMoneyCurrent2;

		numberOfCheckTrue = this.repository.numberShoutsCheckTrue();
		ratioCheckTrue = this.repository.ratioCheckTrue();
		ratioCheckFalse = this.repository.ratioCheckFalse();
		final List<Double> averageShoutMoney = this.repository.averageMoneyCureency();
		final List<Double> deviationShoutMoney = this.repository.deviationMoneyCurrency();
				
		if(averageShoutMoney.size()==2) {
		 	averageMoneyCurrent1 = averageShoutMoney.get(0);
		 	averageMoneyCurrent2 = averageShoutMoney.get(1);
		    } else if(averageShoutMoney.size()<=1 && averageShoutMoney.size()>0) {
		     	averageMoneyCurrent1 = averageShoutMoney.get(0);
		       	averageMoneyCurrent2 = 0.0;
			} else {
			    averageMoneyCurrent1 = 0.0;
			    averageMoneyCurrent2 = 0.0;
			}

		if(deviationShoutMoney.size()==2) {
			deviationMoneyCurrent1 = deviationShoutMoney.get(0);
			deviationMoneyCurrent2 = deviationShoutMoney.get(1);
		} else if(deviationShoutMoney.size()<=1 && deviationShoutMoney.size()>0) {
			deviationMoneyCurrent1 = deviationShoutMoney.get(0);
			deviationMoneyCurrent2 = 0.0;
		} else {
			deviationMoneyCurrent1 = 0.0;
			deviationMoneyCurrent2 = 0.0;
		}


		//Fin parte examen

		averageNumberOfApplicationsPerEmployer = this.repository.averageNumberOfApplicationsPerEmployer();
		averageNumberOfApplicationsPerWorker = this.repository.averageNumberOfApplicationsPerWorker();
		averageNumberOfJobsPerEmployer = this.repository.averageNumberOfJobsPerEmployer();
		ratioOfPendingApplications = this.repository.ratioOfPendingApplications();
		ratioOfAcceptedApplications = this.repository.ratioOfAcceptedApplications();
		ratioOfRejectedApplications = this.repository.ratioOfRejectedApplications();

		numberPublicTask = this.repository.numberPublicTask();
		numberPrivateTask = this.repository.numberPrivateTask();
		averageDurationPeriodTasks = 0.0;
		maximumDurationPeriodTasks = 0.0;
		averageWorkloadTasks = this.repository.averageWorkloadTasks();
		deviationWorkloadTasks = this.repository.deviationWorkloadTasks();
		minimumWorkloadTasks = this.repository.minimumWorkloadTasks();
		maximumWorkloadTasks = this.repository.maximumWorkloadTasks();

		for (final Task t : tasks) {
			averageDurationPeriodTasks = averageDurationPeriodTasks + t.durationPeriodInHours();
		}

		for (final Task t : tasks) {
			//Comprueba si la tarea esta terminada
			if (now.getTime() >= t.getPeriodFinal().getTime()) {
				terminadas.add(t);
			}
		}
		for (final Task t : tasks) {
			//Esto es para calcular el maximo en los Workloads tasks
			if (t.durationPeriodInHours() > maximumDurationPeriodTasks) {
				maximumDurationPeriodTasks = 1.0 * t.durationPeriodInHours();
			}
		}
		//Ponemos que el minimo en el inicio sea el maximo posible, y de ahi vamos decreciendo
		minimumDurationPeriodTasks = maximumDurationPeriodTasks;
		for (final Task t : tasks) {
			//Para calcular el minimo de las workloads tasks
			if (t.durationPeriodInHours() < minimumDurationPeriodTasks) {
				minimumDurationPeriodTasks = 1.0 * t.durationPeriodInHours();
			}
		}
		final List<Double> workloadsLists = new ArrayList<>(); //Creamos una lista de workloads
		for (final Task t : tasks) {
			final Double workload = 1.0 * t.durationPeriodInHours();
			workloadsLists.add(workload);  //Metemos los workloads en la lista
		}
		deviationDurationPeriodTasks = AdministratorDashboardShowService.calculateStandardDeviation(workloadsLists); //Usamos una funcion creada para calcular la desviacion tipica de  los workloads

		final Double noTerminadas = (double) (tasks.size() - terminadas.size());
		final Double term = (double) terminadas.size();

		averageDurationPeriodTasks = AdministratorDashboardShowService.ponerMinutosSobre60(averageDurationPeriodTasks);
		deviationDurationPeriodTasks = AdministratorDashboardShowService.ponerMinutosSobre60(deviationDurationPeriodTasks);
		averageWorkloadTasks = AdministratorDashboardShowService.ponerMinutosSobre60(averageWorkloadTasks);
		deviationWorkloadTasks = AdministratorDashboardShowService.ponerMinutosSobre60(deviationWorkloadTasks);

		
		result = new Dashboard();
		result.setAvegageNumberOfApplicationsPerEmployer(averageNumberOfApplicationsPerEmployer);
		result.setAverageNumberOfApplicationsPerWorker(averageNumberOfApplicationsPerWorker);
		result.setAverageNumberOfJobsPerEmployer(averageNumberOfJobsPerEmployer);
		result.setRatioOfPendingApplications(ratioOfPendingApplications);
		result.setRatioOfAcceptedApplications(ratioOfAcceptedApplications);
		result.setRatioOfRejectedApplications(ratioOfRejectedApplications);
		result.setNumberPublicTask(numberPublicTask);
		result.setNumberPrivateTask(numberPrivateTask);
		result.setNumberFinalTask(term);
		result.setNumberNoFinalTask(noTerminadas);
		result.setAverageDurationPeriodTasks(averageDurationPeriodTasks);
		result.setDeviationDurationPeriodTasks(deviationDurationPeriodTasks);
		result.setMinimumDurationPeriodTasks(minimumDurationPeriodTasks);
		result.setMaximumDurationPeriodTasks(maximumDurationPeriodTasks);
		result.setAverageWorkloadTasks(averageWorkloadTasks);
		result.setDeviationWorkloadTasks(deviationWorkloadTasks);
		result.setMaximumWorkloadTasks(maximumWorkloadTasks);
		result.setMinimumWorkloadTasks(minimumWorkloadTasks);
		
		
		
		//Parte examen
		result.setNumberOfCheckTrue(numberOfCheckTrue);
		result.setRatioCheckTrue(ratioCheckTrue);
		result.setRatioCheckFalse(ratioCheckFalse);
		result.setAverageMoneyCurrent1(averageMoneyCurrent1);
        result.setAverageMoneyCurrent2(averageMoneyCurrent2);
        result.setDeviationMoneyCurrent1(deviationMoneyCurrent1);
        result.setDeviationMoneyCurrent2(deviationMoneyCurrent2);
		//Fin parte examen
        
        
		return result;
	}
	private static double ponerMinutosSobre60(final Double numero) {
		double res;
		final BigDecimal num = BigDecimal.valueOf(numero);
		final long BDhoras = num.longValue();
		final BigDecimal BDminutos = num.remainder(BigDecimal.ONE);

		final String parteMinutos = "" + BDminutos;
		final String parteHoras = "" + BDhoras;

		Double minutos = new Double(parteMinutos);
		final Double horas = new Double(parteHoras);
		minutos = minutos * (60.0 / 100.0);
		res = horas + minutos;

		return res;
	}

	private static double calculateStandardDeviation(final List<Double> lista) {
		//Sumatorio de los valores de la lista
		double sum = 0.0;
		for (int i = 0; i < lista.size(); i++) {
			sum = sum + lista.get(i);
		}
		//Obtiene la media
		final double media = sum / lista.size();

		//Calcula la desviacion estandar
		double standardDeviation = 0.0;
		for (int i = 0; i < lista.size(); i++) {
			standardDeviation += Math.pow(lista.get(i) - media, 2);

		}
		return Math.sqrt(standardDeviation / lista.size());
	}

}