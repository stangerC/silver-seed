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

import com.silver.seed.configuration.descriptor.ConfigurationDescriptor;
import com.silver.seed.configuration.reopsitory.ConfigurationRepository;
import com.silver.seed.common.web.springmvc.controller.HierarchicalMessageController;

import javax.annotation.Resource;

/**
 * 
 * @author Liaojian
 */
@Controller
@RequestMapping(value = "/configurations")
public class ConfigurationManagerController extends HierarchicalMessageController {

    private ConfigurationRepository repository;

    public ConfigurationRepository getRepository() {
        return repository;
    }

    @Resource(name = "configurationRepository")
    public void setRepository(ConfigurationRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		List<ConfigurationDescriptor> descriptors = repository.getDescriptors();

		model.addAttribute("descriptors", descriptors);

		return "configurations";
	}

	@RequestMapping(value = "new", method = RequestMethod.POST)
	public void doCreate(
			@RequestParam(value = "configName", required = true) String configName,
			@RequestParam(value = "configPath", required = true) String configPath,
			Model model) throws ConfigurationException {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(configPath);

		if (url == null) {
			addError("message.error.configuration.path", model);	
			return;
		}

		PropertiesBuilderParametersImpl params = new PropertiesBuilderParametersImpl();
		params.setBeanHelper(BeanHelper.INSTANCE);

		FileHandler handler = params.getFileHandler();
		handler.setURL(url);

		repository.createConfiguration(configName, PropertiesConfiguration.class,
                params);
		
		addSuccess("message.success.common.create", model);
	}

	@RequestMapping(value = "new", method = RequestMethod.GET)
	public String newConfiguration() {
		return "configurations.new";
	}

	@RequestMapping(value = "{configName}")
	public String show(@PathVariable String configName, Model model) {
		ConfigurationDescriptor descriptor = repository.getDescriptor(configName);
		Configuration configuration = repository.getConfiguration(configName);

		model.addAttribute("descriptor", descriptor);
		model.addAttribute("configuration", configuration);

		return "configurations.show";
	}

	@RequestMapping(value = "{configName}/property/{key}", method = RequestMethod.DELETE)
	public void deleteProperty(@PathVariable String configName,
			@PathVariable String key, Model model) {

		Configuration configuration = repository.getConfiguration(configName);
		configuration.clearProperty(key);
		
		addSuccess("message.success.common.delete", model);
	}

	@RequestMapping(value = "{configName}/property/new", method = RequestMethod.POST)
	public void createProperty(@PathVariable String configName,
			@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "value", required = true) String value,
			Model model) {

		if (key == null || "".equals(key.trim())) {
			addError("message.error.property.keyIsNull", model);			
			return;
		}

		if (value == null || "".equals(value.trim())) {
			addError("message.error.property.valueIsNull", model);			
			return;
		}

		Configuration configuration = repository.getConfiguration(configName);
		configuration.addProperty(key, value);
		
		addSuccess("message.success.common.create", model);		
	}

	/**
	 * 
	 * @param configName
	 * @param key
	 * @param value
	 * @param model
	 */
	@RequestMapping(value = "{configName}/property/{key}/update", method = RequestMethod.POST)
	public void updateProperty(@PathVariable String configName,
			@PathVariable String key,
			@RequestParam(value = "value", required = false) String value,
			Model model) {
		if (value == null) {
			addError("message.error.property.valueIsNull", model);			
		}

		Configuration configuration = repository.getConfiguration(configName);
		configuration.setProperty(key, value);
		
		addSuccess("message.success.common.update", model);		
	}

    @RequestMapping(value = "items", method = RequestMethod.GET)
    public String items(Model model) {
        model.addAttribute("repos", repository);
        return "configurations.items";
    }
}
