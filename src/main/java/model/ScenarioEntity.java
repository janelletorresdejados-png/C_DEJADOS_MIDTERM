package model;

/**
 * Represents the Clinic Appointment booking details.
 * Implements a modern immutable Java Record with a maximum of 5 data attributes.
 */
public record ScenarioEntity(
        int id,
        String patientName,
        String doctor,
        double consultFee,
        String status
) {
    // Auxiliary constructor for instantiating new bookings before an ID is auto-assigned
    public ScenarioEntity(String patientName, String doctor, double consultFee, String status) {
        this(0, patientName, doctor, consultFee, status);
    }
}
