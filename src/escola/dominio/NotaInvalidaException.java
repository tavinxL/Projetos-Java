package escola.dominio;


// Unchecked (RuntimeException): nota fora de 0 a 10 indica dado inválido enviado por
// quem chamou o método, e não uma falha externa que o programa consiga contornar.
// Tornar a exception checked obrigaria um try/catch em toda chamada de lancarNota,
// poluindo o código sem benefício, e segue o mesmo critério do IllegalArgumentException.

public class NotaInvalidaException extends RuntimeException {
    public NotaInvalidaException(String message) {
        super(message);
    }
}
