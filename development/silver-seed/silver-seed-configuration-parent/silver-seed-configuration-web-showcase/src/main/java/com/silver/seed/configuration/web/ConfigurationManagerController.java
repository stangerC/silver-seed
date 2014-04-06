package com.silver.seed.configuration.web;

import java.net.URL;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.builder.PropertiesBuilderParametersImpl;
import org.apache.commons.configuration.ex.ConfigurationException;
import org.apache.commons.configuration.io.FileHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.silver.seed.configuration.ConfigurationService;
import com.silver.seed.configuration.descriptor.ConfigurationDescriptor;
import com.silver.seed.configuration.reopsitory.ConfigurationRepository;
import com.silver.seed.web.springmvc.controller.I18NContronller;

/**
 * 
 * @author Liaojian
 */
@Controller
@RequestMapping(value = "/configurations")
public class ConfigurationManagerController extends I18NContronller{

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		ConfigurationRepository repos = ConfigurationService.getBean(null);
		List<ConfigurationDescriptor> descriptors = repos.getDescriptors();

		model.addAttribute("descriptors", descriptors);						
		
		return "configurations";
	}

	@RequestMapping(value = "new", method = RequestMethod.POST)
	public void doCreate(String configName, String configPath, Model model)
			throws ConfigurationException {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(configPath);

		if (url == null) {
			model.addAttribute(MESSAGE_ERROR, getMessage("message.error.configuration.path"));
		}

		PropertiesBuilderParametersImpl params = new PropertiesBuilderParametersImpl();
		params.setBeanHelper(BeanHelper.INSTANCE);

		FileHandler handler = params.getFileHandler();
		handler.setURL(url);

		ConfigurationRepository repos = ConfigurationService
				.getBean(configName);
		repos.createConfiguration(configName, PropertiesConfiguration.class,
				params);

		model.addAttribute(MESSAGE_INFO, getMessage("message.success.common.create"));
	}

	@RequestMapping(value = "new", method = RequestMethod.GET)
	public String newConfiguration() {
		return "configurations.new";
	}

	@RequestMapping(value = "{configName}")
	public String show(@PathVariable String configName, Model model) {
		ConfigurationRepository repos = ConfigurationService
				.getBean(configName);

		ConfigurationDescriptor descriptor = repos.getDescriptor(configName);
		Configuration configuration = repos.getConfiguration(configName);

		model.addAttribute("descriptor", descriptor);
		model.addAttribute("configuration", configuration);

		return "configurations.show";
	}

	@RequestMapping(value = "{configName}/property/{key}", method = RequestMethod.DELETE)
	public void deleteProperty(@PathVariable String configName,
			@PathVariable String key, Model model) {
		ConfigurationRepository repos = ConfigurationService
				.getBean(configName);

		Configuration configuration = repos.getConfiguration(configName);
		configuration.clearProperty(key);

		model.addAttribute(MESSAGE_INFO, "message.success.common.delete");
	}

	@RequestMapping(value = "{configName}/property/new", method = RequestMethod.POST)
	public void createProperty(@PathVariable String configName,
			@RequestParam(value="key", required=true) String key, @RequestParam(value="value", required=true) String value, Model model) {
		ConfigurationRepository repos = ConfigurationService
				.getBean(configName);
		
		if(key == null || "".equals(key.trim())) {
			model.addAttribute(MESSAGE_ERROR, getMessage("message.error.property.keyIsNull"));
			return;
		}
		
		if(value == null || "".equals(value.trim())) {
			model.addAttribute(MESSAGE_ERROR, getMessage("message.error.property.valueIsNull"));
			return;
		}

		Configuration configuration = repos.getConfiguration(configName);
		configuration.addProperty(key, value);		
		
		model.addAttribute(MESSAGE_INFO, getMessage("message.success.common.create"));
	}
	
	/**
	 * 
	 * @param configName
	 * @param key
	 * @param value
	 * @param model
	 */
	@RequestMapping(value = "{configName}/property/{key}/update",  method = RequestMethod.POST)
	public void updateProperty(@PathVariable String configName,
			@PathVariable String key,  @RequestParam(value="value", required=false) String value, Model model) {
		if(value == null) {
			model.addAttribute(MESSAGE_ERROR, getMessage("message.error.property.valueIsNull"));
		}
		ConfigurationRepository repos = ConfigurationService
				.getBean(configName);

		Configuration configuration = repos.getConfiguration(configName);
		configuration.setProperty(key, value);		
		model.addAttribute(MESSAGE_INFO, getMessage("message.success.common.update"));
	}	
}
