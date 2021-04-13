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

import show.isaBack.security.auth.RestAuthenticationEntryPoint;
import show.isaBack.service.userService.CustomUserDetailsService;






@Configuration
// Ukljucivanje podrske za anotacije "@Pre*" i "@Post*" koje ce aktivirati autorizacione provere za svaki pristup metodi
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.
	// BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	

	// Registrujemo authentication manager koji ce da uradi autentifikaciju korisnika za nas
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	// Servis koji se koristi za citanje podataka o korisnicima aplikacije
		@Autowired
		private CustomUserDetailsService jwtUserDetailsService;

	// Handler za vracanje 401 kada klijent sa neodogovarajucim korisnickim imenom i lozinkom pokusa da pristupi resursu
		@Autowired
		private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
		
		// Definisemo uputstvo za authentication managera koji servis da koristi da izvuce podatke o korisniku koji zeli da se autentifikuje,
		//kao i kroz koji enkoder da provuce lozinku koju je dobio od klijenta u zahtevu da bi adekvatan hash koji dobije kao rezultat bcrypt algoritma uporedio sa onim koji se nalazi u bazi (posto se u bazi ne cuva plain lozinka)
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
		}
	

	// Definisemo prava pristupa odredjenim URL-ovima
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	}

	// Generalna bezbednost aplikacije
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
		

		web.ignoring().antMatchers(HttpMethod.GET, "/**");
		//web.ignoring().antMatchers(HttpMethod.GET, "/api/users/**");

		web.ignoring().antMatchers(HttpMethod.GET, "/drug/allDrugs");
		web.ignoring().antMatchers(HttpMethod.POST, "/drug/searchDrugs");
		
		web.ignoring().antMatchers(HttpMethod.GET, "/pharmacy/allPharmacies");
		web.ignoring().antMatchers(HttpMethod.POST, "/pharmacy/searchPharmacies");

		web.ignoring().antMatchers(HttpMethod.POST, "/reg/patsignup");
		web.ignoring().antMatchers(HttpMethod.POST, "/log/login");
		web.ignoring().antMatchers(HttpMethod.GET, "/reg//activeAccountForPatient/**");


		
		//web.ignoring().antMatchers(HttpMethod.GET, "/api/users/activate-patient/**");
		web.ignoring().antMatchers(HttpMethod.GET,"/assets/**" ,"/**/**/*.jpg" , "/**/**/*.png","/clientapp/**", "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js", "/**/**/*.js", "/**/**/*.css");
	}

}
