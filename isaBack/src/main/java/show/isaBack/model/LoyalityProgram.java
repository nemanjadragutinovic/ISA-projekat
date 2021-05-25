package show.isaBack.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="loyaltyprogram")
public class LoyalityProgram {
	
	@Id
    @Column(name = "id")
	private UUID id;
	
	@Column(name = "pointsForAppointment")
	private int pointsForAppointment;

	@Column(name = "pointsForConsulting")
	private int pointsForConsulting;

	@Column(name = "pointsToEnterRegularCathegory")
	private int pointsToEnterRegularCathegory;

	@Column(name = "pointsToEnterLoyalCathegory")
	private int pointsToEnterLoyalCathegory;

	@Column(name = "pointsToEnterVipCathegory")
	private int pointsToEnterVipCathegory;
	
	@Column(name = "appointmentDiscountRegular")
	private int appointmentDiscountRegular;
	
	@Column(name = "drugDiscountRegular")
	private int drugDiscountRegular;
	
	@Column(name = "consultationDiscountRegular")
	private int consultationDiscountRegular;
	
	@Column(name = "appointmentDiscountLoyal")
	private int appointmentDiscountLoyal;
	
	@Column(name = "drugDiscountLoyal")
	private int drugDiscountLoyal;
	
	@Column(name = "consultationDiscountLoyal")
	private int consultationDiscountLoyal;
	
	@Column(name = "appointmentDiscountVip")
	private int appointmentDiscountVip;
	
	@Column(name = "drugDiscountVip")
	private int drugDiscountVip;
	
	@Column(name = "consultationDiscountVip")
	private int consultationDiscountVip;
	
	@Version
	private Long version;

	public LoyalityProgram(UUID id, int pointsForAppointment, int pointsForConsulting,
			int pointsToEnterRegularCathegory, int pointsToEnterLoyalCathegory, int pointsToEnterVipCathegory,
			int appointmentDiscountRegular, int drugDiscountRegular, int consultationDiscountRegular,
			int appointmentDiscountLoyal, int drugDiscountLoyal, int consultationDiscountLoyal,
			int appointmentDiscountVip, int drugDiscountVip, int consultationDiscountVip) {
		super();
		this.id = id;
		this.pointsForAppointment = pointsForAppointment;
		this.pointsForConsulting = pointsForConsulting;
		this.pointsToEnterRegularCathegory = pointsToEnterRegularCathegory;
		this.pointsToEnterLoyalCathegory = pointsToEnterLoyalCathegory;
		this.pointsToEnterVipCathegory = pointsToEnterVipCathegory;
		this.appointmentDiscountRegular = appointmentDiscountRegular;
		this.drugDiscountRegular = drugDiscountRegular;
		this.consultationDiscountRegular = consultationDiscountRegular;
		this.appointmentDiscountLoyal = appointmentDiscountLoyal;
		this.drugDiscountLoyal = drugDiscountLoyal;
		this.consultationDiscountLoyal = consultationDiscountLoyal;
		this.appointmentDiscountVip = appointmentDiscountVip;
		this.drugDiscountVip = drugDiscountVip;
		this.consultationDiscountVip = consultationDiscountVip;
		
	}
	
	public LoyalityProgram(int pointsForAppointment, int pointsForConsulting,
			int pointsToEnterRegularCathegory, int pointsToEnterLoyalCathegory, int pointsToEnterVipCathegory,
			int appointmentDiscountRegular, int drugDiscountRegular, int consultationDiscountRegular,
			int appointmentDiscountLoyal, int drugDiscountLoyal, int consultationDiscountLoyal,
			int appointmentDiscountVip, int drugDiscountVip, int consultationDiscountVip) {
		
		this(UUID.randomUUID(), pointsForAppointment, pointsForConsulting, pointsToEnterRegularCathegory,pointsToEnterLoyalCathegory , pointsToEnterVipCathegory
				 ,appointmentDiscountRegular, drugDiscountRegular, consultationDiscountRegular, appointmentDiscountLoyal,
				 drugDiscountLoyal, consultationDiscountLoyal, appointmentDiscountVip,  drugDiscountVip, consultationDiscountVip);
		
	}

	public LoyalityProgram() {
		
		
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public int getPointsForAppointment() {
		return pointsForAppointment;
	}

	public void setPointsForAppointment(int pointsForAppointment) {
		this.pointsForAppointment = pointsForAppointment;
	}

	public int getPointsForConsulting() {
		return pointsForConsulting;
	}

	public void setPointsForConsulting(int pointsForConsulting) {
		this.pointsForConsulting = pointsForConsulting;
	}

	public int getPointsToEnterRegularCathegory() {
		return pointsToEnterRegularCathegory;
	}

	public void setPointsToEnterRegularCathegory(int pointsToEnterRegularCathegory) {
		this.pointsToEnterRegularCathegory = pointsToEnterRegularCathegory;
	}

	public int getPointsToEnterLoyalCathegory() {
		return pointsToEnterLoyalCathegory;
	}

	public void setPointsToEnterLoyalCathegory(int pointsToEnterLoyalCathegory) {
		this.pointsToEnterLoyalCathegory = pointsToEnterLoyalCathegory;
	}

	public int getPointsToEnterVipCathegory() {
		return pointsToEnterVipCathegory;
	}

	public void setPointsToEnterVipCathegory(int pointsToEnterVipCathegory) {
		this.pointsToEnterVipCathegory = pointsToEnterVipCathegory;
	}

	public int getAppointmentDiscountRegular() {
		return appointmentDiscountRegular;
	}

	public void setAppointmentDiscountRegular(int appointmentDiscountRegular) {
		this.appointmentDiscountRegular = appointmentDiscountRegular;
	}

	public int getDrugDiscountRegular() {
		return drugDiscountRegular;
	}

	public void setDrugDiscountRegular(int drugDiscountRegular) {
		this.drugDiscountRegular = drugDiscountRegular;
	}

	public int getConsultationDiscountRegular() {
		return consultationDiscountRegular;
	}

	public void setConsultationDiscountRegular(int consultationDiscountRegular) {
		this.consultationDiscountRegular = consultationDiscountRegular;
	}

	public int getAppointmentDiscountLoyal() {
		return appointmentDiscountLoyal;
	}

	public void setAppointmentDiscountLoyal(int appointmentDiscountLoyal) {
		this.appointmentDiscountLoyal = appointmentDiscountLoyal;
	}

	public int getDrugDiscountLoyal() {
		return drugDiscountLoyal;
	}

	public void setDrugDiscountLoyal(int drugDiscountLoyal) {
		this.drugDiscountLoyal = drugDiscountLoyal;
	}

	public int getConsultationDiscountLoyal() {
		return consultationDiscountLoyal;
	}

	public void setConsultationDiscountLoyal(int consultationDiscountLoyal) {
		this.consultationDiscountLoyal = consultationDiscountLoyal;
	}

	public int getAppointmentDiscountVip() {
		return appointmentDiscountVip;
	}

	public void setAppointmentDiscountVip(int appointmentDiscountVip) {
		this.appointmentDiscountVip = appointmentDiscountVip;
	}

	public int getDrugDiscountVip() {
		return drugDiscountVip;
	}

	public void setDrugDiscountVip(int drugDiscountVip) {
		this.drugDiscountVip = drugDiscountVip;
	}

	public int getConsultationDiscountVip() {
		return consultationDiscountVip;
	}

	public void setConsultationDiscountVip(int consultationDiscountVip) {
		this.consultationDiscountVip = consultationDiscountVip;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	

}
