package br.com.processboss.core.util;

public class CronConverterUtil {

	public static String converterToText(String cronExp){

		StringBuffer exit = new StringBuffer();
		String[] vetExp = cronExp.split(" ") ;  
		
		//DIAS
		String[] week = vetExp[5].split("/");
		String[] days = vetExp[3].split("/");
		
		if(week[0].equals("?")){
			if(days.length == 1){
				if(days[0].equals("*"))
					exit.append("Todos os dias");
				else 
					exit.append(days[0] + " dia(s)");
			}else{
				if(days[1].equals("1")){
					exit.append("Todos os dias");
				}else{
					exit.append("A cada " + days[1] + " dia(s)");
				}
			}
		}else{
			String[] weekDays = week[0].toString().split(",");
			
			if(weekDays.length == 7){
				exit.append("Todos os dias ");
			}else{
				exit.append("No(s) dia(s) ");
				for (String day : weekDays) {
					exit.append(translateWeekDay(day) + ", ");
				}
			}
		}
		
		//HORAS
		String[] hours = vetExp[2].split("/");
		if(hours.length == 1){
			if(!hours[0].equals("*")) exit.append(" a(s) " + hours[0] + " hora(s)");
		}else{
			exit.append(" a cada " + hours[1] + " hora(s)");
		}

		//MINUTOS
		String[] minuts = vetExp[1].split("/");
		if(minuts.length == 1){
			if(!minuts[0].equals("0")) exit.append(" e " + minuts[0] + " minuto(s)");
		}else{
			exit.append(" a cada " + minuts[1] + " minuto(s)");
		}
		
		return exit.toString();
	}

	/**
	 * 
	 * @param Dia da semana no formato String. É previsto que seja passado como parâmetro ou um valor numérico de 0 a 6 ou em sigla, por exemplo: SUN
	 * @return Nome do dia da semana
	 */
	private static String translateWeekDay(String dia) {
		if(dia.equals("SUN") || dia.equals("0")) return "Domingo";
		else if(dia.equals("MON") || dia.equals("1")) return "Segunda-feira";
		else if(dia.equals("TUE") || dia.equals("2")) return "Terça-feira";
		else if(dia.equals("WED") || dia.equals("3")) return "Quarta-feira";
		else if(dia.equals("THU") || dia.equals("4")) return "Quinta-feira";
		else if(dia.equals("FRI") || dia.equals("5")) return "Sexta-feira";
		else if(dia.equals("SAT") || dia.equals("6")) return "Sábado";
		
		else return null;
	}

	
}
