/**
 * <b>ATMOSPHERE</b> - http://www.atmosphere-eubrazil.eu/
 *** <p>
 * <b>Trustworthiness Monitoring & Assessment Framework</b>
 * Component: Admin
 * <p>
 * Repository: https://github.com/eubr-atmosphere/tma-framework License:
 * https://github.com/eubr-atmosphere/tma-framework/blob/master/LICENSE
 * <p>
 * <p>
 */
package eu.atmosphere.tma.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.atmosphere.tma.admin.dto.Partner;
import eu.atmosphere.tma.admin.util.PropertiesManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is a Rest Controller. It handles every request made to the
 * server related to the actuators.
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 * @author Jose A. D. Pereira  <josep@dei.uc.pt>
 * @author Rui Silva <rfsilva@student.dei.uc.pt>
 * @author Nuno Antunes     <nmsa@dei.uc.pt>
 *
 */
@CrossOrigin
@RestController
public class PartnerAdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerAdminController.class);
    @Autowired
    public PropertiesManager properties;

    @GetMapping("/getpartners")
    public ResponseEntity<Map> getPartners(HttpServletResponse response) {
        ArrayList<Partner> partnerList = new ArrayList<>();
        Map<String, Integer> partnersDictionary = properties.getPartnerList();

        partnerList.add(new Partner("No Partner", -1));
        partnersDictionary.forEach((partnerName, partnerId) -> {
            partnerList.add(new Partner(partnerName, partnerId));
        });

        HashMap<String, ArrayList<Partner>> partnersJson = new HashMap<>();
        partnersJson.put("partnerList", partnerList);

        return new ResponseEntity<>(
                partnersJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }
}
