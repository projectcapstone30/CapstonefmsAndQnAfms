package com.capstone.fmssystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FMS.Model.Employee;
import com.FMS.Model.Event;
import com.capstone.fmssystem.repo.Repository.EmployeeRepo;
import com.capstone.fmssystem.repo.Repository.EventRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

@Service
public class EventServiceImpl implements EventService {

	private static String[] column_names = { "event_id", "base_location",
			"beneficiary_name", "council_name", "event_name",
			"event_description", "event_date", "employee_id", "employee_name",
			"volunteer_hours", "travel_hours", "lives_impacted",
			"business_unit", "event_status", "iiep_category" };
	List<Event> allEvents = new ArrayList<Event>();

	@Autowired
	EventRepo eventRepo;
	@Autowired
	EmployeeRepo empRepo;

	@Override
	public Flux<Event> getAllEvents() {
		Flux<Event> events = eventRepo.findAll();
		return events;
	}

	@Override
	public Flux<Event> searchEventsById(String eventId) {
		return eventRepo.findAllByEvent_id(eventId);
	}

	@Override
	public Flux<Event> searchByVolunteerHours(int volunteerHours) {
		return eventRepo.findAllByVolunteer_hours(volunteerHours);
	}

	@Override
	public Mono<Event> findEventById(int id) {
		return eventRepo.findById((long) id);
	}

	@Override
	public Mono<String> SendMail() {
		generateExcel();
		System.out.println("SimpleEmail Start");
		String smtpHostServer = "smtp.gmail.com";
		Properties props = System.getProperties();

		props.put("mail.smtp.host", smtpHostServer);
		props.put("mail.smtp.port", 465);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.ssl.trust", smtpHostServer);

		props.put("mail.smtp.auth", true);
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("irshad.ayoubi@gmail.com",
						"************");
			}
		};
		Session session = Session.getInstance(props, auth);
		sendMail(session, "sh.irshaad@gmail@gmail.com", "Do It Fast",
				"Mail sent delivered");
		return Mono.just("kindly verify from  your email");
	}

	public static void sendMail(Session session, String toEmail,
			String subject, String body) {
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress("swetha.snk7@gmail.com"));

			msg.setReplyTo(InternetAddress.parse(toEmail, false));
			msg.setSubject(subject, "UTF-8");
			msg.setText(body, "UTF-8");
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toEmail, false));
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(body);
			Multipart multipart = new MimeMultipart();
			messageBodyPart = new MimeBodyPart();
			String filename = "JsonExcel.xlsx";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			msg.setContent(multipart);
			Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateExcel() {
		Flux<Event> values = eventRepo.findAll();
		values.subscribe((next) -> {
			allEvents.add(next);
		});
		copyToExcel(allEvents);
	}

	private void copyToExcel(List<Event> output) {

		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		Sheet sheet = workbook.createSheet("Events");

		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 15);
		font.setColor(IndexedColors.RED.getIndex());

		CellStyle style = workbook.createCellStyle();
		style.setFont(font);
		Row headerRow = sheet.createRow(0);

		for (int i = 0; i < column_names.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(column_names[i]);
			cell.setCellStyle(style);
		}

		CellStyle dateCStyle = workbook.createCellStyle();
		dateCStyle.setDataFormat(createHelper.createDataFormat().getFormat(
				"dd-MM-yyyy"));

		int rowNum = 1;
		for (Event event : output) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(event.getEvent_id());

			row.createCell(1).setCellValue(event.getBase_location());

			row.createCell(2).setCellValue(event.getBeneficiary_name());

			row.createCell(3).setCellValue(event.getCouncil_name());

			row.createCell(4).setCellValue(event.getEvent_name());

			row.createCell(5).setCellValue(event.getEvent_description());

			Cell dateOfBirthCell = row.createCell(6);
			dateOfBirthCell.setCellValue(event.getEvent_date());
			dateOfBirthCell.setCellStyle(dateCStyle);

			row.createCell(7).setCellValue(event.getEmployee_id());

			row.createCell(8).setCellValue(event.getEmployee_name());

			row.createCell(9).setCellValue(event.getVolunteer_hours());

			row.createCell(10).setCellValue(event.getTravel_hours());

			row.createCell(11).setCellValue(event.getLives_impacted());

			row.createCell(12).setCellValue(event.getBusiness_unit());

			row.createCell(13).setCellValue(event.getEvent_status());

			row.createCell(14).setCellValue(event.getIiep_category());
		}

		for (int i = 0; i < column_names.length; i++) {
			sheet.autoSizeColumn(i);
		}
		FileOutputStream fio;
		try {
			fio = new FileOutputStream("JsonExcelSheet.xlsx");
			workbook.write(fio);
			fio.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
