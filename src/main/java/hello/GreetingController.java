package hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	// autowired > utk ambil object di server utk di assign ke object greeting x
	@Autowired
	private Greeting x;

	@Autowired
	private EntityManagerFactory em;
	
	//cross origin : semua bisa akses web service
	@CrossOrigin(origins= {"*"})
	@RequestMapping ("/actors")
	public List<Actor> allActor (){
		return em.createEntityManager().createQuery("from Actor").getResultList();
	}
	
//	@Autowired
//	private EntityManagerFactory ef;
	
	@CrossOrigin(origins= {"*"})
	@RequestMapping ("/film")
	public List<Film> allFilm (){
		return em.createEntityManager().createQuery("from Film").getResultList();
	}
	
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return x;
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
	}

	@RequestMapping("/data")
	public List<String> dataNegara(@RequestParam("pre") String prefix) {
		List<String> data = new ArrayList<>();
		data.add("Indonesia");
		data.add("Malaysia");
		data.add("Brunei");
		data.add("Timor  Leste");

		return data.stream().filter(line -> line.startsWith(prefix)).collect(Collectors.toList());
	}

	@RequestMapping("/countries")
	public String getCountries() throws IOException {
		// URL url = new URL ("http://www.webservicex.net/country.asmx/GetCountries");
		// URLConnection connection = url.openConnection();
		// connection.setDoOutput(true);

		URL url = new URL("http://www.webservicex.net/globalweather.asmx/GetCitiesByCountry?CountryName=string");
		URLConnection connection = url.openConnection();
		// connection.setDoOutput(true);
		// connection.setRequestProperty("Content-Type",
		// "application/x-www-form-urlencoded");
		// connection.setRequestProperty("Content-Length", "0");

		InputStream stream = url.openConnection().getInputStream();
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader buffer = new BufferedReader(reader);

		String line;
		StringBuilder builder = new StringBuilder();

		while ((line = buffer.readLine()) != null) {
			builder.append(line);
		}

		return builder.toString();
		// return new BufferedReader(new
		// InputStreamReader(stream)).lines().collect(Collectors.joining("\n"));

	}

}

// SOAP & REST
// REST >> HTTP GET & HTTP POST
// web service >> contoh penggunaan konversi realtime