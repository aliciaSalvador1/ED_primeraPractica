package ule.edi.travel;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ule.edi.model.*;

public class TravelArrayImpl implements Travel {
	
	private static final Double DEFAULT_PRICE = 100.0;
	private static final Byte DEFAULT_DISCOUNT = 25;
	private static final Byte CHILDREN_EXMAX_AGE = 18;
	private Date travelDate;
	private int nSeats;
	
	private Double price;    
	private Byte discountAdvanceSale;   
   	
	private Seat[] seats;

	private int numAdultos;
	private int numNiños;

	
		
	
	
   public TravelArrayImpl(Date date, int nSeats){
	   
		this.travelDate = date;
		this.nSeats = nSeats;
		this.price = DEFAULT_PRICE;
		this.discountAdvanceSale = DEFAULT_DISCOUNT;

		seats = new Seat[nSeats];
	  	   
	   
	   
   }
   
   
   public TravelArrayImpl(Date date, int nSeats, Double price, Byte discount){

	   this.travelDate = date;
	   this.nSeats = nSeats;
	   this.price = price;
	   this.discountAdvanceSale = discount;

	   seats = new Seat[nSeats];

	   
   }






@Override
public Byte getDiscountAdvanceSale() {

	return this.discountAdvanceSale;
}


@Override
public int getNumberOfSoldSeats() {
	
	int asienstosVendidios = 0;

	for(int i = 0; i < nSeats; i ++ ){

		if(seats[i]!=null){
			asienstosVendidios ++;
		}
	}

	return asienstosVendidios;
}


@Override
public int getNumberOfNormalSaleSeats() {

	int ventaNormal = 0; 

	for(int i = 0; i < nSeats; i++){

		if(seats[i]!=null && !seats[i].getAdvanceSale()){
			ventaNormal++;
		}
	}
	return ventaNormal;
}


@Override
public int getNumberOfAdvanceSaleSeats() {

	int ventaRebajada = 0;

	for (int i = 0; i < nSeats; i++){
		if(seats[i]!=null && seats[i].getAdvanceSale()){
			ventaRebajada ++;
		}
	}
	return ventaRebajada;
}


@Override
public int getNumberOfSeats() {

	return nSeats;
}


@Override
public int getNumberOfAvailableSeats() {
	
	int asientosLibres = 0;
	for (int i=0; i < nSeats; i++){
		if(seats[i]==null){
			asientosLibres++;
		}
	}
	return asientosLibres;
}

@Override
public Seat getSeat(int pos) {
	

	if(pos <= 0 || pos > nSeats){
		return null;
	}

	return seats[pos -1];
}


@Override
public Person refundSeat(int pos) {
	

	if(pos<= 0 || pos > nSeats || seats[pos -1] == null ){
		return null;
	}

	Person billeteDevuelto = seats[pos -1].getHolder();

	seats[pos -1] = null;
		
	if(billeteDevuelto != null){
		int años = billeteDevuelto.getAge();
		if(isChildren(años)){
			numNiños--;
		}
		else if(isAdult(años)){
			numAdultos--;
		}
	}

	return billeteDevuelto;
}



private boolean isChildren(int age) {


	if(age < CHILDREN_EXMAX_AGE){
		return true;
	}

	return false;
}

private boolean isAdult(int age) {
	
	boolean iAdult=false;
	if(age >= CHILDREN_EXMAX_AGE){
		iAdult=true;
	}

	return iAdult;
}


@Override
public List<Integer> getAvailableSeatsList() {
	
	List<Integer> lista=new ArrayList<Integer>(nSeats);

	for(int i = 0; i < nSeats; i++){

		if(seats[i]==null){
			lista.add(i+1);
		}
	}
	
	
	return lista;
}


@Override
public List<Integer> getAdvanceSaleSeatsList() {

	List<Integer> lista=new ArrayList<Integer>(nSeats);

	for (int i = 0; i < nSeats; i++){
		if(seats[i]!= null && seats[i].getAdvanceSale()){
			lista.add(i+1); 
		}
	}	
	
	return lista;
}


@Override
public int getMaxNumberConsecutiveSeats() {
	
	int nConsecutivo = 0;
	int nMax = 0;

	for (int i = 0; i < nSeats - 1; i ++){
		if(seats[i]==null){
			nConsecutivo++;	
			}
		else{
			nMax = Math.max(nMax, nConsecutivo);
			nConsecutivo = 0;	
			}
		}
		nMax = Math.max(nMax, nConsecutivo);

	nConsecutivo = 0;

	for(int i= nSeats - 1; i >= 0; i--){
		if(seats[i] == null){
			nConsecutivo++;
		}
		else{
			nMax = Math.max(nMax, nConsecutivo);
			nConsecutivo = 0;
		}
	}	
	
	return nMax;
}




@Override
public boolean isAdvanceSale(Person p) {
	
	boolean rebaja = false;

	if(p != null){
		int posicion= getPosPerson(p.getNif());
		if(posicion != -1 && seats[posicion -1].getAdvanceSale()){
			rebaja = true;
		}
	}
	return rebaja;
}


@Override
public Date getTravelDate() {
	
	return this.travelDate;
}


@Override
public boolean sellSeatPos(int pos, String nif, String name, int edad, boolean isAdvanceSale) {

	if(pos<= 0 || pos >  nSeats || seats[pos -1] != null || nif == null || nif.isEmpty()){
		return false;
	}

	for(int i = 0; i < nSeats; i++){
		if(seats[i] != null && seats[i].getHolder().getNif().equals(nif)){
			return false;
		}
	}
	
	Person holder = new Person(nif, name, edad);

	Seat nuevoSitio = new Seat(isAdvanceSale, holder);

	seats[pos -1] = nuevoSitio;

	if(isChildren(edad)){
		numNiños++;
	}
	else if(isAdult(edad)){
		numAdultos++;
	}

	return true;
}

@Override
public int getNumberOfChildren() {
	
	return 	numNiños;
}

//PREGUNTAR EN CLASE 
@Override
public int getNumberOfAdults() {
	
	return numAdultos;
}



@Override
public Double getCollectionTravel() {
	double dineroTotal = 0.0;
	
	for(int i = 0; i < nSeats; i++){
		if(seats[i] !=null){
				dineroTotal += getSeatPrice(seats[i]);
		}
	}
	
	return dineroTotal;
}


@Override
public int getPosPerson(String nif) {
	

	int posicion = -1;

	for(int i= 0; i< nSeats; i++){
		
		if(seats[i] != null && seats[i].getHolder() != null&& seats[i].getHolder().getNif().equals(nif)){
			posicion = i+1;
			continue; 
		}
	}

	return posicion;	
}


@Override
public int sellSeatFrontPos(String nif, String name, int edad, boolean isAdvanceSale) {
	

	for(int i = 0; i < nSeats; i++){

		if(seats[i]==null){
			Person holder = new Person (nif, name, edad);
			Seat nuevoSitio = new Seat(isAdvanceSale, holder);
			seats[i] = nuevoSitio;
			return i + 1;
		}
	}

	return -1;
}


@Override
public int sellSeatRearPos(String nif, String name, int edad, boolean isAdvanceSale) {
	
	for(int i = seats.length - 1; i>=0; i-- ){

		if(seats[i]==null){
			Person holder = new Person(nif, name, edad);
			Seat nuevoSitio = new Seat(isAdvanceSale, holder);
			seats[i]= nuevoSitio;
			return i + 1;
		}
	}

	return -1;
}




@Override
public Double getSeatPrice(Seat seat) {

	double precioFinal = 0.0;
	double descuento = discountAdvanceSale + 0.0;;

	if(seat != null && seat.getAdvanceSale()){
		descuento = 1 - descuento / 100;
		precioFinal = price * descuento;
	}
	else if(seat != null){
		precioFinal = price;
	}
	return precioFinal;
}


@Override
public double getPrice() {
	return this.price;
}


}	