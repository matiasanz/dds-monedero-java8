package dds.monedero.model;

public class exceptions
{
	class MaximaCantidadDepositosException extends RuntimeException {

		  /**
		 * 
		 */
		private static final long serialVersionUID=1L;

		public MaximaCantidadDepositosException(int limiteDeDepositos) {
		    super("Ya excedio los " + limiteDeDepositos + " depositos diarios");
		  }

		}

	class MaximoExtraccionDiarioException extends RuntimeException {
		  /**
		 * 
		 */
		private static final long serialVersionUID=1L;

		public MaximoExtraccionDiarioException(double limite) {
		    super("No puede extraer mas de $ " + 1000
		            + " diarios, límite: " + limite);
		  }
		}

	class MontoNegativoException extends RuntimeException {
	  /**
		 * 
		 */
	  private static final long serialVersionUID=1L;

	  public MontoNegativoException(double cuanto) {
	    super( cuanto + ": el monto a ingresar debe ser un valor positivo");
	  }
	}

	class SaldoMenorException extends RuntimeException {
		  /**
		 * 
		 */
		private static final long serialVersionUID=1L;

		public SaldoMenorException(String message) {
		    super(message);
		  }
	}
}
