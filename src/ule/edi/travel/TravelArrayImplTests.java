package ule.edi.travel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.*;

import ule.edi.model.*;

public class TravelArrayImplTests {

	private DateFormat dformat = null;
	private TravelArrayImpl e, ep;
	
	
	private Date parseLocalDate(String spec) throws ParseException {
        return dformat.parse(spec);
	}

	public TravelArrayImplTests() {
		
		dformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	@Before 
	public void testBefore() throws Exception{
	    e = new TravelArrayImpl(parseLocalDate("24/02/2020 17:00:00"), 110);
	    ep = new TravelArrayImpl(parseLocalDate("24/02/2020 17:00:00"), 4);

	}
	
	@Test
	public void testEventoVacio() throws Exception {
		
	    Assert.assertTrue(e.getNumberOfAvailableSeats()==110);
	    Assert.assertEquals(110, e.getNumberOfAvailableSeats());
	    Assert.assertEquals(0, e.getNumberOfAdults());
		Assert.assertTrue(e.getNumberOfAdults() == 0);
	    Assert.assertEquals(0, e.getNumberOfChildren());
		Assert.assertTrue(e.getNumberOfChildren()== 0); 
	    Assert.assertEquals(100.0,0.0, e.getPrice());
		  	
	}


	@Test
	public void test2Constructor() throws Exception{
		 TravelArrayImpl  e2 = new TravelArrayImpl(parseLocalDate("24/02/2020 17:00:00"), 110, 200.0, (byte) 20);
		 Assert.assertEquals(parseLocalDate("24/02/2020 17:00:00"), e2.getTravelDate());
	    Assert.assertEquals( 200.0,0.0, e2.getPrice());
	    Assert.assertEquals((byte)20,(byte) e2.getDiscountAdvanceSale());
		Assert.assertEquals(110, e2.getNumberOfSeats());
	}
	
	
	@Test 
	public void test2ConstructorCollect() throws Exception{
		 TravelArrayImpl  e2 = new TravelArrayImpl(parseLocalDate("24/02/2018 17:00:00"), 110, 200.0, (byte) 20);
		 Assert.assertTrue(e2.sellSeatPos(1, "10203040A","Alice", 34,false));	//venta normal
		 Assert.assertTrue(e2.sellSeatPos(2, "10203040B","Alice", 34,true));	//venta anticipada
		 Assert.assertEquals(2, e2.getNumberOfSoldSeats());				 
	    Assert.assertEquals(130.0,0.0,e2.getCollectionTravel());
		}
	  
	// test getDiscountAdvanceSale
	
	@Test
	public void testGetDiscountAdvanceSale() throws Exception {
		
	    Assert.assertTrue(e.getDiscountAdvanceSale()==25);
	}
	
	// test getDate
	
	@Test
	public void testGetDate() throws Exception {
		
	    Assert.assertEquals(parseLocalDate("24/02/2020 17:00:00"), e.getTravelDate());
	    Assert.assertEquals(110,e.getNumberOfAvailableSeats());
	    Assert.assertEquals(0, e.getNumberOfAdults());
	    Assert.assertEquals(0, e.getNumberOfSoldSeats());	
		
	}
	
	// test getNumber....
	@Test
	public void testsellSeatPos1Adult() throws Exception{	
	    Assert.assertEquals(0, e.getNumberOfAdults());
		Assert.assertTrue(e.sellSeatPos(4, "10203040A","Alice", 18,false));	//venta normal
	    Assert.assertEquals(1,e.getNumberOfAdults());  
	    Assert.assertEquals(0,e.getNumberOfAdvanceSaleSeats());	
	    Assert.assertEquals(1,e.getNumberOfNormalSaleSeats());  
	    Assert.assertEquals(1,e.getNumberOfSoldSeats());	
	    Assert.assertEquals(110,e.getNumberOfSeats());  
	   
	}
	

	@Test
	public void testsellSeatPos1Children() throws Exception{	
	    Assert.assertEquals(0, e.getNumberOfChildren());
		Assert.assertTrue(e.sellSeatPos(4, "10203040A","Alice", 7,false));	//venta normal
	    Assert.assertEquals(1,e.getNumberOfChildren());  
	    Assert.assertEquals(0,e.getNumberOfAdvanceSaleSeats());	
	    Assert.assertEquals(1,e.getNumberOfNormalSaleSeats());  
	    Assert.assertEquals(1,e.getNumberOfSoldSeats());	
	    Assert.assertEquals(110,e.getNumberOfSeats());  
	   
	}
	
	// TEST OF sellSeatPos
	@Test
	public void testsellSeatPosPosCero() throws Exception{		
	   Assert.assertEquals(false,e.sellSeatPos(0, "10203040A","Alice", 34,false));	//venta normal  
	}
	
	@Test
	public void testsellSeatPosPosMayorMax() throws Exception{		
	   Assert.assertEquals(false,e.sellSeatPos(e.getNumberOfAvailableSeats()+1, "10203040A","Alice", 34,false));	//venta normal  
	}
	@Test
	public void testsellSeatPosPosOcupada() throws Exception{		
	   Assert.assertEquals(true, e.sellSeatPos(5, "10203040A","Alice", 34,false));	//venta normal  
	   Assert.assertEquals(false, e.sellSeatPos(5, "10203040A","Alice", 34,false));	//venta normal  
	}
	
	
	//TEST OF GET COLLECTION
	 
	@Test
	public void testgetCollectionAnticipadaYnormal() throws Exception{
		Assert.assertEquals(true, e.sellSeatPos(1, "1010", "AA", 10, true));
		Assert.assertEquals(true, e.sellSeatPos(4, "10101", "AA", 10, false));
		
		Assert.assertTrue(e.getCollectionTravel()==175.0);					
	}
	
	// TEST List
	@Test
	public void testGetListEventoCompleto() throws Exception{		
		   Assert.assertEquals(true, ep.sellSeatPos(1, "10203040A","Alice", 34,true));	//venta normal  
		   Assert.assertEquals(true, ep.sellSeatPos(2, "10203040B","Alice", 34,true));	//venta normal  
		   Assert.assertEquals(true, ep.sellSeatPos(3, "10203040C","Alice", 34,false));	//venta normal  
		   Assert.assertEquals(true, ep.sellSeatPos(4, "10203040D","Alice", 34,false));	//venta normal  
		   Assert.assertEquals("[]", ep.getAvailableSeatsList().toString());
		   Assert.assertEquals("[1, 2]", ep.getAdvanceSaleSeatsList().toString());
	}
	
	
	
	//TEST DE GETPRICE
	
	@Test
	public void testgetPrice() throws Exception{
		Assert.assertEquals(true,e.sellSeatPos(1, "1010", "AA", 10, true));
		Assert.assertEquals(true,e.sellSeatPos(4, "10101", "AA", 10, false));
		Assert.assertEquals(100.0,0.0,e.getSeatPrice(e.getSeat(4)));
		Assert.assertEquals(75.0,0.0,e.getSeatPrice(e.getSeat(1)));
		}
	
	
	//tests REFUND 
	
		
		@Test
		public void testREFUNDCero() throws Exception{
			Assert.assertEquals(true,e.sellSeatPos(1, "1010", "AA", 10, true));	
			Assert.assertEquals(null,e.refundSeat(0));
			}
		
		
		@Test
		public void testrefundOk() throws Exception{
			Person p=new Person("1010", "AA",10);
			Assert.assertEquals(true, e.sellSeatPos(1, "1010", "AA", 10, true));	
			Assert.assertEquals(p,e.refundSeat(1));
			}
		
		
	
		
	// TEST GetPosPerson
	@Test
		public void testGetPosPersonLleno() throws Exception{		
			   Assert.assertEquals(true,ep.sellSeatPos(1, "10203040","Alic", 34,true));	//venta anticipada  
			   Assert.assertEquals(true,ep.sellSeatPos(3, "10203040A","Alice", 34,false));	//venta normal  
			   Assert.assertEquals(true,ep.sellSeatPos(4, "10203040B","Alice", 34,false));	//venta normal  
			   Assert.assertEquals(-1,ep.getPosPerson("10205040"));
			   Assert.assertEquals(false,ep.isAdvanceSale(new Person("10203040A","Alice", 34)));
			   Assert.assertEquals(true,ep.isAdvanceSale(new Person("10203040","Alic", 34)));
			   Assert.assertEquals(false,ep.isAdvanceSale(new Person("10202531", "Ana", 31)));
			   Assert.assertEquals(3,ep.getPosPerson("10203040A"));
					 
		}
	
	@Test
		public void testSellSeatFrontPos()throws Exception{
			Assert.assertEquals(0, e.sellSeatFrontPos("111111111A", "Alicia", 19, false)); 
			Assert.assertEquals(0, ep.sellSeatFrontPos("111111112A", "Alicia", 19, false));
	}
		
	@Test
		public void testSellSeatRearPos()throws Exception{													 //PORQUE ESTE TEST NO TIRA
		Assert.assertEquals(108, e.sellSeatRearPos("111111111A", "Alicia", 19, false));
		Assert.assertEquals(4, ep.sellSeatRearPos("111111112A", "Alicia", 19, false));
		
	}
	
	@Test 
		public void testGetNumberOfSoldSeats() {
		
		e.sellSeatPos(1,"1111111111A", "Alicia", 19 ,false);
		e.sellSeatPos(2,"1111111112A", "Alicia", 19 ,true);
		e.sellSeatPos(3,"1111111113A", "Alicia", 19 ,false);
		
		
		Assert.assertEquals(3, e.getNumberOfSoldSeats());

		e.sellSeatPos(2, "1111111112A", "Alicia", 19 ,true);
		Assert.assertEquals(3, e.getNumberOfSoldSeats());

		e.sellSeatPos(112, "1111111112A", "Ali", 19, true);
		Assert.assertEquals(3, e.getNumberOfSoldSeats());
	}
	
	@Test 
		public void testGetNumberOfNormalSale() throws Exception {
		
		e.sellSeatPos(1, "111111111A", "Alicia", 19, false);
		e.sellSeatPos(2, "111111111B", "Alicia", 19, true);
		e.sellSeatPos(3, "111111111C", "Alicia", 19, false);
		e.sellSeatPos(4, "111111111D", "Alicia", 19, false);
		
		Assert.assertEquals(3, e.getNumberOfNormalSaleSeats());
		
	}
	
	@Test 
		public void testGetNumberOfAdvanceSale() throws Exception {
		
		e.sellSeatPos(1, "111111111A", "Alicia", 19, true);
		e.sellSeatPos(2, "111111111B", "Alicia", 19, true);
		e.sellSeatPos(3, "111111111C", "Alicia", 19, false);
		e.sellSeatPos(4, "111111111D", "Alicia", 19, true);
		
		Assert.assertEquals(3, e.getNumberOfAdvanceSaleSeats());
	}

	@Test 																					
		public void testGetNumberOfSeats() throws Exception {
		
			Assert.assertEquals(110, e.getNumberOfSeats());	
			Assert.assertEquals(4, ep.getNumberOfSeats());
	}

	@Test 
	public void testGetNumberOfAvailableSeats() throws Exception{

		Assert.assertEquals(110, e.getNumberOfAvailableSeats());

		e.sellSeatPos(1, "111111111A", "Alicia", 19, true);
		e.sellSeatPos(2, "111111112A", "Alicia", 19, false);
		e.sellSeatPos(3, "111111113A", "Alicia", 19, true);
		
		Assert.assertEquals(107, e.getNumberOfAvailableSeats());

	}

	@Test 
	public void testGetSeat() throws Exception{

		Assert.assertNull(ep.getSeat(0)); //Fuera de limites
		Assert.assertNull(ep.getSeat(5)); //Fuera de limites 

		ep.sellSeatPos(1, "111111111A", "Alicia", 19, false); //Creamos una persona nueva 
		Assert.assertNotNull(ep.getSeat(1));  //Comprobamos que su posicion es no nula 
		Assert.assertEquals(1, ep.getPosPerson("111111111A")); // y que su posicion se corresponde 

		Assert.assertNull(ep.getSeat(2)); //Deveria ser null porque no esta ocupado
	}


	@Test
	public void testIsChildren() throws Exception{

		ep.sellSeatPos(1, "111111111A", "Alicia", 8, false);
		Assert.assertTrue(true);

		ep.sellSeatPos(2, "111111111B", "Ali", 19, true);
		Assert.assertFalse(false);
	}

	@Test 
	public void testIsAdult() throws Exception{

		ep.sellSeatPos(1, "111111111A", "Alicia", 8, false);
		Assert.assertFalse(false);

		ep.sellSeatPos(2, "111111111B", "Ali", 19, true);
		Assert.assertTrue(true);
	}

	@Test
	public void testGetMaxNumberConsecutiveSeats(){

		//Asientos vacios 
		Assert.assertEquals(0, ep.getMaxNumberConsecutiveSeats());

		//Solo un asiento ocupado
		ep.sellSeatPos(1, "111111111A", "Alicia	", 19, false);
		Assert.assertEquals(1, ep.getMaxNumberConsecutiveSeats());

		//Todos los asientos ocupados
		ep.sellSeatPos(1, "111111111A", "Alicia	", 19, false);
		ep.sellSeatPos(2, "111111112A", "Alicia	", 19, true);
		ep.sellSeatPos(3, "111111113A", "Alicia	", 19, false);
		ep.sellSeatPos(4, "111111114A", "Alicia	", 19, true);
		
		Assert.assertEquals(4, ep.getMaxNumberConsecutiveSeats());
	}
	
	@Test
	public void testIsAdvanceSale() throws Exception{
		
		ep.sellSeatPos(1, "111111111A", "Alicia", 19, true);
		Assert.assertTrue(true);
		
		ep.sellSeatPos(1, "111111112A", "Alicia", 19, false);
		Assert.assertFalse(false);
	}
	
	@Test 
	public void testGetNumberOfChildren() throws Exception {

		ep.sellSeatPos(1, "111111111A", "Alicia", 19, true);
		Assert.assertEquals(0, ep.getNumberOfChildren());

		ep.sellSeatPos(2, "111111112A", "Alicia", 8, true);
		Assert.assertEquals(1, ep.getNumberOfChildren());

	}

	@Test 
	public void testGetNumberOfAdults() throws Exception {

		ep.sellSeatPos(1, "111111111A", "Alicia", 8, true);
		Assert.assertEquals(0, ep.getNumberOfAdults());

		ep.sellSeatPos(2, "111111112A", "Alicia", 19, true);
		Assert.assertEquals(1, ep.getNumberOfAdults());
	}

}
	



