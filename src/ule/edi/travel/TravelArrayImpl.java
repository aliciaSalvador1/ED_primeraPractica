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
	
	private Double price;    // precio de entradas 
	private Byte discountAdvanceSale;   // descuento en venta anticipada (0..100)
   	
	private Seat[] seats;

	private int numAdultos;
	private int numNiños;

	
		
	
	
   public TravelArrayImpl(Date date, int nSeats){
	   //TODO 
	   // utiliza los precios por defecto: DEFAULT_PRICE y DEFAULT_DISCOUNT definidos en esta clase
	   
		this.travelDate = date;
		this.nSeats = nSeats;
		this.price = DEFAULT_PRICE;
		this.discountAdvanceSale = DEFAULT_DISCOUNT;

		seats = new Seat[nSeats];
	  	   
	   
	   
   }
   
   
   public TravelArrayImpl(Date date, int nSeats, Double price, Byte discount){
	   //TODO 
	   // Debe crear el array de asientos

	   this.travelDate = date;
	   this.nSeats = nSeats;
	   this.price = price;
	   this.discountAdvanceSale = discount;

	   seats = new Seat[nSeats];

	   
   }






@Override
public Byte getDiscountAdvanceSale() {
	// TODO Auto-generated method stub
	return this.discountAdvanceSale;
}


@Override
public int getNumberOfSoldSeats() {
	// TODO Auto-generated method stub
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
	// TODO Auto-generated method stub

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
	// TODO Auto-generated method stub

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
	// TODO Auto-generated method stub
	return nSeats;
}


@Override
public int getNumberOfAvailableSeats() {
	// TODO Auto-generated method stub
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
	// TODO Auto-generated method stub

	if(pos <= 0 || pos > nSeats){
		return null;
	}

	return seats[pos -1];
}


@Override
public Person refundSeat(int pos) {
	// TODO Auto-generated method stub

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
	// TODO Auto-generated method stub

	if(age < CHILDREN_EXMAX_AGE){
		return true;
	}

	return false;
}

private boolean isAdult(int age) {
	// TODO Auto-generated method stub
	boolean iAdult=false;
	if(age >= CHILDREN_EXMAX_AGE){
		iAdult=true;
	}

	return iAdult;
}


@Override
public List<Integer> getAvailableSeatsList() {
	// TODO Auto-generated method stub
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
	// TODO Auto-generated method stub
	List<Integer> lista=new ArrayList<Integer>(nSeats);

	for (int i = 0; i < nSeats; i++){
		if(seats[i]!= null && seats[i].getAdvanceSale()){
			lista.add(i+1); //Se le suma uno porque si no no es el correspondiente numero de asiento
		}
	}	
	
	return lista;
}


@Override
public int getMaxNumberConsecutiveSeats() {
	// TODO Auto-generated method stub
	int nConsecutivo = 0;
	int nMax = 0;

	for (int i = 0; i < nSeats; i ++){
		if(seats[i]!=null){
			nConsecutivo++;	
			if(nConsecutivo > nMax){
				nMax = nConsecutivo;
			}
		}
		else{

			nConsecutivo = 0;
				
		}
	}
	return nMax;
}




@Override
public boolean isAdvanceSale(Person p) {
	// TODO Auto-generated method stub
	
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
	// TODO Auto-generated method stub
	return this.travelDate;
}


@Override
public boolean sellSeatPos(int pos, String nif, String name, int edad, boolean isAdvanceSale) {
	// TODO Auto-generated method stub

	if(pos<= 0 || pos >  nSeats || seats[pos -1] != null || nif == null || nif.isEmpty()){
		return false;
	}

	double seatPrice = isAdvanceSale ? DEFAULT_PRICE - DEFAULT_DISCOUNT : DEFAULT_PRICE;

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

//PREGUNTAR EN CLASE
@Override
public int getNumberOfChildren() {
	// TODO Auto-generated method stub
	return 	numNiños;
}

//PREGUNTAR EN CLASE 
@Override
public int getNumberOfAdults() {
	// TODO Auto-generated method stub
	return numAdultos;
}



@Override
public Double getCollectionTravel() {
	// TODO Auto-generated method stub
	double dineroTotal = 0;

	for(int i = 0; i < nSeats; i++){
		if(seats[i] !=null){
				dineroTotal += getSeatPrice(seats[i]);
		}
	}
	
	return dineroTotal;
}


@Override
public int getPosPerson(String nif) {
	// TODO Auto-generated method stub

	int posicion = -1;

	for(int i= 0; i< nSeats; i++){
		
		if(seats[i] != null && seats[i].getHolder() != null&& seats[i].getHolder().getNif().equals(nif)){
			posicion = i+1;
			break; //Para salir del bucle
		}
	}

	return posicion;	
}


@Override
public int sellSeatFrontPos(String nif, String name, int edad, boolean isAdvanceSale) {
	// TODO Auto-generated method stub

	for(int i = 0; i < nSeats; i++){

		if(seats[i]==null){
			return i;
		}
	}

	return -1;
}


@Override
public int sellSeatRearPos(String nif, String name, int edad, boolean isAdvanceSale) {
	// TODO Auto-generated method stub
	for(int i = seats.length; i>=0; i-- ){

		if(seats[i]==null){
			return i;
		}
	}

	return -1;
}




@Override
public Double getSeatPrice(Seat seat) {
	// TODO Auto-generated method stub

	double precio = 0;

	if(seat != null && seat.getAdvanceSale()){
		precio = DEFAULT_PRICE - DEFAULT_DISCOUNT;
	}
	else if(seat != null){
		precio = DEFAULT_PRICE;
	}
	return precio;
}


@Override
public double getPrice() {
	return this.price;
}


}	