const stripe = Stripe('pk_test_51QyRp0GWsQLen8ya7Kyljt57TuhlYUTLsgM4FG3AQTZwrMp0rpc0W1vqodXoNdIL1WuEC0tVqzaRaT1C8YQUsBQa00hZFDlDPz')
const paymentButton = document.querySelector('#paymentButton');

paymentButton.addEventListener('click', () => {
	stripe.redirectToCheckout({
		sessionId: sessionId
	})
});