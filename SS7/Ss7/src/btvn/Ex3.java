package btvn;

public class Ex3 {

    interface PaymentMethod {
        void pay(double amount);
    }

    interface CODPayable extends PaymentMethod {
        void processCOD(double amount);
    }

    interface CardPayable extends PaymentMethod {
        void processCard(double amount);
    }

    interface EWalletPayable extends PaymentMethod {
        void processEWallet(double amount);
    }

    static class CODPayment implements CODPayable {
        public void processCOD(double amount) {
            System.out.println("Xu ly thanh toan COD: " + amount + " - Thanh cong");
        }

        public void pay(double amount) {
            processCOD(amount);
        }
    }

    static class CreditCardPayment implements CardPayable {
        public void processCard(double amount) {
            System.out.println("Xu ly thanh toan the tin dung: " + amount + " - Thanh cong");
        }

        public void pay(double amount) {
            processCard(amount);
        }
    }

    static class MomoPayment implements EWalletPayable {
        public void processEWallet(double amount) {
            System.out.println("Xu ly thanh toan MoMo: " + amount + " - Thanh cong");
        }

        public void pay(double amount) {
            processEWallet(amount);
        }
    }

    static class PaymentProcessor {
        public void process(PaymentMethod paymentMethod, double amount) {
            paymentMethod.pay(amount);
        }
    }

    public static void main(String[] args) {

        PaymentProcessor processor = new PaymentProcessor();

        PaymentMethod cod = new CODPayment();
        processor.process(cod, 500000);

        PaymentMethod card = new CreditCardPayment();
        processor.process(card, 1000000);

        PaymentMethod momo = new MomoPayment();
        processor.process(momo, 750000);

        PaymentMethod lspTest = new MomoPayment();
        processor.process(lspTest, 1000000);
    }
}
