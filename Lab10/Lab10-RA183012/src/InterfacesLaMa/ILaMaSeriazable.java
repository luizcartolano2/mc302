package InterfacesLaMa;

import io.Escritor;
import java.io.IOException;

/**
 * Created by duducartolano on 06/06/17.
 */
public interface ILaMaSeriazable {
    void escreveArquivo(Escritor escritor) throws IOException;
}
