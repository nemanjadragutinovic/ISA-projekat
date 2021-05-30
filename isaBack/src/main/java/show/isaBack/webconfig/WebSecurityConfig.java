package show.isaBack.webconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import show.isaBack.security.TokenUtils;
import show.isaBack.security.auth.RestAuthenticationEntryPoint;
import show.isaBack.security.auth.TokenAuthenticationFilter;
import show.isaBack.service.userService.CustomUserDetailsService;



@Configuration
//Ukljucivanje podrske za anotacije "@Pre*" i "@Post*" koje ce aktivirati autorizacione provere za svaki pristup metodi
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.
	// BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Servis koji se koristi za citanje podataka o korisnicima aplikacije
	@Autowired
	private CustomUserDetailsService jwtUserDetailsService;

	// Handler za vracanje 401 kada klijent sa neodogovarajucim korisnickim imenom i lozinkom pokusa da pristupi resursu
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	// Registrujemo authentication manager koji ce da uradi autentifikaciju korisnika za nas
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// Definisemo uputstvo za authentication managera koji servis da koristi da izvuce podatke o korisniku koji zeli da se autentifikuje,
	//kao i kroz koji enkoder da provuce lozinku koju je dobio od klijenta u zahtevu da bi adekvatan hash koji dobije kao rezultat bcrypt algoritma uporedio sa onim koji se nalazi u bazi (posto se u bazi ne cuva plain lozinka)
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	// Injektujemo implementaciju iz TokenUtils klase kako bismo mogli da koristimo njene metode za rad sa JWT u TokenAuthenticationFilteru
	@Autowired
	private TokenUtils tokenUtils;

	// Definisemo prava pristupa odredjenim URL-ovima
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// komunikacija izmedju klijenta i servera je stateless posto je u pitanju REST aplikacija
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				// sve neautentifikovane zahteve obradi uniformno i posalji 401 gresku
				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

				// svim korisnicima dopusti da pristupe putanjama /auth/**, (/h2-console/** ako se koristi H2 baza) i /api/foo
				.authorizeRequests().antMatchers("/auth/**").permitAll().antMatchers("/h2-console/**").permitAll().antMatchers("/api/foo").permitAll()
				
				// za svaki drugi zahtev korisnik mora biti autentifikovan
				.anyRequest().authenticated().and()
				// za development svrhe ukljuci konfiguraciju za CORS iz WebConfig klase
				.cors().and()

				// umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT tokena umesto cistih korisnickog imena i lozinke (koje radi BasicAuthenticationFilter)
				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
						BasicAuthenticationFilter.class);
		// zbog jednostavnosti primera
		http.csrf().disable();

	
	
				
	
	}

	// Generalna bezbednost aplikacije
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
		

		//web.ignoring().antMatchers(HttpMethod.GET, "/**");
		//web.ignoring().antMatchers(HttpMethod.GET, "/api/users/**");

		web.ignoring().antMatchers(HttpMethod.GET, "/drug/allDrugs");
		web.ignoring().antMatchers(HttpMethod.POST, "/drug/searchDrugs");
		
		web.ignoring().antMatchers(HttpMethod.GET, "/pharmacy/allPharmacies");
		web.ignoring().antMatchers(HttpMethod.POST, "/pharmacy/searchPharmacies");
		
		web.ignoring().antMatchers(HttpMethod.POST, "/reg/patsignup");
		web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");
		
		web.ignoring().antMatchers(HttpMethod.POST, "/users/changeFirstPassword");
		
		
		
		web.ignoring().antMatchers(HttpMethod.GET, "/pharmacy");
		web.ignoring().antMatchers(HttpMethod.PUT, "/drug");
		web.ignoring().antMatchers(HttpMethod.GET, "/drug");
		web.ignoring().antMatchers(HttpMethod.PUT, "/drug/manufacturer");
		web.ignoring().antMatchers(HttpMethod.PUT, "/drug/replacement");
		web.ignoring().antMatchers(HttpMethod.PUT, "/drug/ingredient");
		web.ignoring().antMatchers(HttpMethod.GET, "/drug/drugkind");
		web.ignoring().antMatchers(HttpMethod.GET, "/drug/drugformat");
		web.ignoring().antMatchers(HttpMethod.GET, "/drug/manufacturers");
		
		web.ignoring().antMatchers(HttpMethod.POST, "/ingredients");
		
		web.ignoring().antMatchers(HttpMethod.GET, "/drug/loyaltyProgram/**");
		
	
		
		//web.ignoring().antMatchers(HttpMethod.GET, "/order/getAllOrders");
		
	//	web.ignoring().antMatchers(HttpMethod.PUT, "/offer/drugsCheck");
		
		
		
		web.ignoring().antMatchers(HttpMethod.GET, "/reg/activeAccountForPatient/**");


		
		//web.ignoring().antMatchers(HttpMethod.GET, "/api/users/activate-patient/**");
		web.ignoring().antMatchers(HttpMethod.GET,"/assets/**" ,"/**/**/*.jpg" , "/**/**/*.png","/clientapp/**", "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js", "/**/**/*.js", "/**/**/*.css");
	}

}
