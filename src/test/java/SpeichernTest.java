import bankprojekt.verarbeitung.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import java.sql.ResultSet;
import java.sql.Statement;

class SpeichernTest {

    @Test
    public void speichernNeuTest() throws Exception {
        Konto konto = new Girokonto();
        Statement statement = Mockito.mock(Statement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.next()).thenReturn(false);
        Mockito.when(statement.executeQuery(ArgumentMatchers.anyString())).thenReturn(resultSet);
        konto.speichern(statement);
        Mockito.verify(resultSet).insertRow();
        Mockito.verify(resultSet, Mockito.times(0)).updateRow();
    }

    @Test
    public void speichernVorhandenTest() throws Exception {
        Konto konto = new Girokonto();
        Statement statement = Mockito.mock(Statement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(statement.executeQuery(ArgumentMatchers.anyString())).thenReturn(resultSet);
        konto.speichern(statement);
        Mockito.verify(resultSet).updateRow();
        Mockito.verify(resultSet, Mockito.times(0)).insertRow();
    }

}
