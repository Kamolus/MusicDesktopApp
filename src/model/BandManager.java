package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Klasa reprezentująca menedżera zespołu muzycznego.
 * Dziedziczy po EventManager oraz implementuje interfejs IScout.
 * Może zawierać maksymalnie dwa aktywne kontrakty z zespołami.
 */
public class BandManager extends EventManager implements IScout {

    private static final int MAX_CONTRACTS = 2; // Maksymalna liczba kontraktów, które może mieć menedżer
    private List<Contract> contracts = new ArrayList<>(); // Lista aktywnych kontraktów
    private String phoneNumber; // Numer telefonu menedżera

    /**
     * Konstruktor menedżera zespołu.
     * Jeśli numer telefonu jest nieprawidłowy, obiekt zostaje usunięty z extentu.
     */
    public BandManager(String name, String email, LocalDate hireDate, double salary, String areaOfOperation, String phoneNumber) {
        super(name, email, hireDate, salary, areaOfOperation);
        try {
            setPhoneNumber(phoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
            removeFromExtent();
        }
    }

    /**
     * Ustawia numer telefonu z walidacją.
     */
    @Override
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        this.phoneNumber = phoneNumber;
    }

    /**
     * Zwraca numer telefonu.
     */
    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Przypisuje dostępnego muzyka do zespołu, jeżeli menedżer ma kontrakt z danym zespołem
     * i znajdzie się dostępny muzyk danego typu.
     */
    @Override
    public void findAndAssignMusicianToBand(Band band, MusicianType desiredType) {
        // Sprawdzenie, czy menedżer ma kontrakt z tym zespołem
        if (contracts.stream().noneMatch(contract -> contract.getBand().equals(band))) {
            throw new IllegalArgumentException("Manager does not have contract with this band");
        }

        // Szukanie dostępnego muzyka danego typu
        Optional<Musician> musician = ObjectExtent
                .getExtentFromClass(Musician.class).stream()
                .filter(m -> m.isAvailable() && m.hasType(desiredType))
                .findFirst();

        // Jeżeli znaleziono – dodaj muzyka do zespołu
        musician.ifPresentOrElse(
                m -> {
                    band.addMusician(m);
                    System.out.println("Dodano muzyka: " + m.getName() + " do zespołu: " + band.getName());
                },
                () -> System.out.println("Brak dostępnych muzyków dla typu: " + desiredType)
        );
    }

    /**
     * Usuwa kontrakt z listy kontraktów menedżera oraz rozłącza go z obiektem Contract.
     */
    public void removeContract(Contract contract) {
        if (contracts.contains(contract)) {
            contracts.remove(contract);
            contract.removeContract(); // metoda usuwająca referencję do managera i zespołu z kontraktu
        }
    }

    /**
     * Tworzy nowy kontrakt z zespołem, jeżeli taki jeszcze nie istnieje.
     */
    public void signContract(PopularBand band, int duration, double basePayment, double payPerPerformance) {
        if (contracts.stream().noneMatch(contract -> contract.getBand().equals(band))) {
            new Contract(this, band, duration, basePayment, payPerPerformance); // kontrakt dodaje się automatycznie do managera przez konstruktor
        }
    }

    /**
     * Dodaje kontrakt do listy kontraktów menedżera.
     * Jeżeli menedżer przekroczy limit, wyrzuca wyjątek.
     */
    public void addContract(Contract contract) {
        if (contracts.size() >= MAX_CONTRACTS) {
            throw new IllegalArgumentException("Maximum number of contracts reached");
        }
        contracts.add(contract);
    }

    /**
     * Zwraca informację tekstową o menedżerze.
     */
    @Override
    public String toString() {
        return "BandManager{" +
                "contracts=" + contracts +
                '}';
    }
}
