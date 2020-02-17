package style;

public class DeckImpl implements Deck {

	// Instance fields

	private final Card[] newvar;
	private int _num_left_to_deal;

	// Constructor
	public DeckImpl() {
		_num_left_to_deal = 52;
		newvar = new Card[_num_left_to_deal];

		int cidx = 0;
		for (final Card.Suit s : Card.Suit.values()) {
			for (int rank = 2; rank <= CardImpl.ACE; rank++) {
				// System.out.println(rank);
				newvar[cidx] = new CardImpl(rank, s);
				cidx += 1;
			}
		}

		for (int i = 0; i < newvar.length; i++) {
			final int swap_idx = i + (int) (Math.random() * (newvar.length - i));
			final Card tmp = newvar[i];

			newvar[i] = newvar[swap_idx];

			newvar[swap_idx] = tmp;
		}
	}

	// Returns PokerHand
	@Override
	public PokerHand dealHand() {
		if (hasHand() == false) {
			throw new RuntimeException("Deck does not have enough cards to deal another hand");
		}

		final Card[] hand_cards = new Card[5];
		for (int i = 0; i < hand_cards.length; i++) {

			hand_cards[i] = dealNextCard();
		}
		final PokerHand h = new PokerHandImpl(hand_cards);
		return h;
	}

	// Returns card
	@Override
	public Card dealNextCard() {
		if (_num_left_to_deal == 0) {
			throw new RuntimeException();
		}
		final Card dealtCard = newvar[nextUndealtIndex()];
		_num_left_to_deal -= 1;
		return dealtCard;
	}

	// Returns void
	@Override
	public void findAndRemove(Card c) {
		if (_num_left_to_deal == 0) {
			return;
		}

		for (int i = nextUndealtIndex(); i < 52; i++) {
			if (newvar[i].equals(c)) {
				final Card tmp = newvar[i];
				newvar[i] = newvar[nextUndealtIndex()];
				newvar[nextUndealtIndex()] = tmp;
				dealNextCard();
				return;
			}
		}
		return;
	}

	// Returns boolean
	@Override
	public boolean hasHand() {
		boolean bool = false;
		if (_num_left_to_deal >= 5) {
			bool = true;
		}
		return bool;
	}

	private int nextUndealtIndex() {
		final int x = 52 - _num_left_to_deal;
		return x;
	}
}
