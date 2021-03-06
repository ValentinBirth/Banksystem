package bankprojekt.verarbeitung;

/**
 * alle Kontoarten, die es jemals in meinem Bankprogramm geben wird
 * @author Birth
 *
 */
public enum Kontoart {
	GIROKONTO("ganz großer Dispo"),
	SPARBUCH("total viele Zinsen"),
	FESTGELDKONTO("ham wa nich");
	
	/**
	 * @return the werbebotschaft
	 */
	public String getWerbebotschaft() {
		return werbebotschaft;
	}

	private final String werbebotschaft;
	
	Kontoart(String werbebotschaft)  // <- trotzdem private
	{
		this.werbebotschaft = werbebotschaft;
	}
	
}
