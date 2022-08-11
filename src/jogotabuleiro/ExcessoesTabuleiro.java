package jogotabuleiro;

public class ExcessoesTabuleiro extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcessoesTabuleiro(String msg) {
		super(msg);
	}
}
