package dds.monedero.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import dds.monedero.model.Movimientos.Deposito;
import dds.monedero.model.Movimientos.Extraccion;
import dds.monedero.model.Movimientos.Movimiento;
import dds.monedero.model.exceptions.MaximaCantidadDepositosException;
import dds.monedero.model.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.model.exceptions.MontoNegativoException;
import dds.monedero.model.exceptions.SaldoMenorException;

public class Cuenta {

  private double saldoInicial = 0;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta(double montoInicial) {
	  saldoInicial = montoInicial;
  }

  public void poner(double cuanto) {
    this.validarDeposito(cuanto);
    Movimiento deposito = new Deposito(cuanto);
    this.agregarMovimiento(deposito);
  }
  
  public void sacar(double cuanto) {
    this.validarExtraccion(cuanto);
    Movimiento extraccion = new Extraccion(cuanto);
    this.agregarMovimiento(extraccion);
  }

  public void agregarMovimiento(Movimiento nuevoMovimiento) {
    movimientos.add(nuevoMovimiento);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
	  Stream<Movimiento> extracciones = this.getExtracciones();  
	  return this.montoDeHoy(extracciones, fecha);
  }
  
  public double getMontoDepositadoA(LocalDate fecha) {
	  Stream<Movimiento> depositos = this.getDepositos();  
	  return this.montoDeHoy(depositos, fecha);	        
  }
  
  private double montoDeHoy(Stream<Movimiento> unaLista, LocalDate fecha){
	  return unaLista.filter(movimiento->movimiento.esDeLaFecha(fecha))
      .mapToDouble(Movimiento::getMonto)
      .sum();
  }

  private Stream<Movimiento> getExtracciones(){
	  return this.getMovimientos().stream().filter(movimiento -> !movimiento.isDeposito());
  }
  
  private Stream<Movimiento> getDepositos(){
	  return movimientos.stream().filter(movimiento -> movimiento.isDeposito());
  }
  
  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
	LocalDate fecha =  LocalDate.now();
    return saldoInicial + this.getMontoDepositadoA(fecha)- this.getMontoExtraidoA(fecha);
  }
  
  public void setSaldo(double saldo){
	  saldoInicial = saldo;
  }
  
  void validarDeposito(double cuanto){
	  if (cuanto <= 0) 
		  throw new MontoNegativoException(cuanto);
	    
	  int limiteDeDepositos = 3;
	  if (this.getDepositos().count() >= limiteDeDepositos)
		  throw new MaximaCantidadDepositosException(limiteDeDepositos);
  }
  
  void validarExtraccion(double cuanto){
	if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto);
    }
	    
    double saldoPorExtraer = getSaldo() - cuanto;
    if (saldoPorExtraer < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }

    double limite = 1000 - getMontoExtraidoA(LocalDate.now());
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException(limite);
    }
  }

}
