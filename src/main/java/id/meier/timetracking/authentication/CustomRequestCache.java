package id.meier.timetracking.authentication;

import id.meier.timetracking.util.AuthenticationUtils;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HttpSessionRequestCache that avoids saving internal framework requests.
 */
class CustomRequestCache extends HttpSessionRequestCache {
	private final AuthenticationUtils authenticationUtils;

	CustomRequestCache(AuthenticationUtils authenticationUtils) {
		this.authenticationUtils = authenticationUtils;
	}

	/**
	 * {@inheritDoc}
	 *
	 * If the method is considered an internal request from the framework, we skip
	 * saving it.
	 *
	 * @see AuthenticationUtils#isFrameworkInternalRequest(HttpServletRequest)
	 */
	@Override
	public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
		if (!authenticationUtils.isFrameworkInternalRequest(request)) {
			super.saveRequest(request, response);
		}
	}

}