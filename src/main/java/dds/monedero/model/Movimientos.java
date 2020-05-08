package dds.monedero.model;

import java.time.LocalDate;

public class Movimientos {  
  
  abstract class Movimiento{
	  LocalDate fecha;
	  //En ningún lenguaje de programación usen jamás doubles para modelar dinero en el mundo real
	  //siempre usen numeros de precision arbitraria, como BigDecimal en Java y similares
	  double monto;
	  
	  Movimiento(double monto) {
		this.fecha = LocalDate.now();
		this.monto = monto;
	  }

	  abstract double calcularValor(Cuenta cuenta);    
      
	  abstract boolean isDeposito();
	  
      public boolean esDeLaFecha(LocalDate fecha) {
  	    return this.fecha.equals(fecha);
  	  }
      
      public double getMonto() {
    	  return monto;
      }

      public LocalDate getFecha() {
    	  return fecha;
      }
  }
  
  class Deposito extends Movimiento{
	
	public Deposito(double unMonto){
		super(unMonto);
	}
	
	@Override
	public double calcularValor(Cuenta cuenta) {
		 return cuenta.getSaldo() + monto;
	}	
	
	public boolean fueDepositado(LocalDate fechaDeposito){
		return this.esDeLaFecha(fechaDeposito);
	}

	@Override
	boolean isDeposito()
	{
		return true;
	}
  }
  
  class Extraccion extends Movimiento{
	  Extraccion(double unMonto){
			super(unMonto);
	  }
	  
	  @Override
	  public double calcularValor(Cuenta cuenta) {
		  return cuenta.getSaldo() - getMonto();
	  }
	  
	  public boolean fueExtraido(LocalDate fecha) {
		  return esDeLaFecha(fecha);
	  }
	  
	  @Override
	  boolean isDeposito()
	  {
		  return false;
	  }
  }
}
