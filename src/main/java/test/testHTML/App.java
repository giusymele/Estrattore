package test.testHTML;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {

	public static void main(String[] args) {
		System.out.println("Hello from the main method");
		// path domande da elaborare
		String html = "/Users/giusy/Documents/test.html";
		String rispostaCorretta = "";
		List<String> risposteInserite = new ArrayList();
		File in = new File(html);
		Document doc;
		try {
			// Creo nuovo file
			FileWriter nuovoFile = new FileWriter("/Users/giusy/Documents/nuovoFile.html");
			nuovoFile.write(
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n"
							+ "<html>\n" + "<head>\n"
							+ "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n"
							+ "  <meta http-equiv=\"Content-Style-Type\" content=\"text/css\">\n"
							+ "  <title></title>\n" + "  <meta name=\"Generator\" content=\"Cocoa HTML Writer\">\n"
							+ "  <meta name=\"CocoaVersion\" content=\"2113\">\n" + "  </style>\n" + "\n"
							+ "  <link rel=\"stylesheet\" href=\"cssTest.css\">\n" + "</head>\n" + "<body>");

			doc = Jsoup.parse(in, "UTF-8");

			Elements tableElements = doc.select("table");

			for (int x = 0; x < tableElements.size(); x++) {
				Elements tableRowElements = tableElements.get(x).select(":not(thead) tr");				
				Elements tds = tableElements.get(x).select("th");
				// Controllo se la domanda non è già stata inserita
				if (!risposteInserite.contains(tds.get(1).text())) {
					rispostaCorretta="";
					nuovoFile.write("Domanda:" + (x + 1));
					System.out.println("Domanda:" + (x + 1));
					risposteInserite.add(tds.get(1).text());
					nuovoFile.write("<table><tr><th>" + tds.get(1).text() + "</th></tr>");
					System.out.println(tds.get(1).text());
					for (int i = 1; i < 5; i++) {
						Element row = tableRowElements.get(i);
						Elements rowItems = row.select("td");
						if (rowItems.size() >= 1) {
							if (!row.getElementsByClass("correct").isEmpty()
									|| !row.getElementsByClass("answer_correct").isEmpty() ||!row.getElementsByClass("success").isEmpty()) {

								nuovoFile.write(
										"<tr><td class= answer_correct>" + i + ")" + rowItems.get(1).text() + "</td></tr><br>");
								System.out.println(i + ")" + rowItems.get(1).text());
								rispostaCorretta = "la risposta corretta:" + i;

							} else {
								nuovoFile.write("<tr><td>" + i + ")" + rowItems.get(1).text() + "</td></tr><br>");
								System.out.println(i + ")" + rowItems.get(1).text());
							}
						}
					}
					nuovoFile.write("<tr><td>"+rispostaCorretta+"</tr></td>" 
							+ "</table><br>");
					System.out.println(rispostaCorretta + "\n");
				}

			}
			nuovoFile.write("<br>" + "Domande trovate " + risposteInserite.size());
			System.out.println();
			System.out.println("Domande trovate " + risposteInserite.size());

			nuovoFile.write("</body>\n" + "</html>");
			nuovoFile.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
