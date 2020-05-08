package dds.monedero.model;

import org.junit.Before;
import org.junit.Test;

import dds.monedero.model.exceptions.MaximaCantidadDepositosException;
import dds.monedero.model.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.model.exceptions.MontoNegativoException;
import dds.monedero.model.exceptions.SaldoMenorException;

public class MonederoTest {
  private Cuenta cuenta;

  @Before
  public void init() {
    cuenta = new Cuenta(0);
  }

  @Test
  public void Poner() {
    cuenta.poner(1500);
  }

  @Test(expected = MontoNegativoException.class)
  public void PonerMontoNegativo() {
    cuenta.poner(-1500);
  }

  @Test
  public void TresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
  }

  @Test(expected = MaximaCantidadDepositosException.class)
  public void MasDeTresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    cuenta.poner(245);
  }

  @Test(expected = SaldoMenorException.class)
  public void ExtraerMasQueElSaldo() {
    cuenta.setSaldo(90);
    cuenta.sacar(1001);
  }

  @Test(expected = MaximoExtraccionDiarioException.class)
  public void ExtraerMasDe1000() {
    cuenta.setSaldo(5000);
    cuenta.sacar(1001);
  }

  @Test(expected = MontoNegativoException.class)
  public void ExtraerMontoNegativo() {
    cuenta.sacar(-500);
  }

}