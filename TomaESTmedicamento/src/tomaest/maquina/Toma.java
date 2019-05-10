package tomaest.maquina;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Representa uma toma, isto é, quais os medicamentos e a quantidade de cada um
 *  que se tem de tomar numa dada data.
 */
public class Toma {

	private LocalDateTime quando;
	private ArrayList<TomaParcial> partes = new ArrayList<TomaParcial>();
	
	public Toma(LocalDateTime q){
		this.quando = q;
	}
	
	public LocalDateTime getQuando() {
		return quando;
	}
	
	public void addPartes(TomaParcial tP){
		partes.add(tP);
	}
	
	public ArrayList<TomaParcial> getPartes(){ 
		return partes;
	}

	public TomaParcial getPartesIdx(int idx){ 
		return partes.get(idx);
	}
	
	@Override
	public String toString() {
		String res1 = "";
		for (TomaParcial x : partes)
			res1 +="\n" + quando + " | " + x.toString();
		return res1;
	}
	
}
