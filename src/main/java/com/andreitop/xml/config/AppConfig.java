package com.andreitop.xml.config;

import com.andreitop.xml.mount.Mount;
import com.andreitop.xml.mount.Tiger;
import com.andreitop.xml.mount.Wolf;
import com.andreitop.xml.unit.Human;
import com.andreitop.xml.unit.Orc;
import com.andreitop.xml.unit.Troll;
import com.andreitop.xml.unit.Unit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@ComponentScan(basePackages = "com.andreitop.xml")
@PropertySource("classpath:config/heroes.properties")
public class AppConfig {

    @Value("${character.created}")
    private String characterCreatedDate;

    @Bean
    public Unit knight() {
        return new Human(shadowTiger(), "thunderFury", "soulBlade");
    }

    @Bean
    public Mount shadowTiger() {
        return new Tiger();
    }

    @Bean
    public Mount frostWolf() {
        return new Wolf();
    }

    @Bean
    public Unit trall() {
        Orc orc = new Orc(frostWolf());
        orc.setWeapon("furryAxe");
        orc.setColorCode(9);
        return orc;
    }

    @Bean
    public DateFormat dateFormatter () {
        return new SimpleDateFormat("dd/mm/yyyy");
    }

    @Bean
    public Set<Mount> trollMountSet() {
        Set<Mount> setOfMounts = new HashSet<>();
        setOfMounts.add(frostWolf());
        setOfMounts.add(shadowTiger());
        return setOfMounts;
    }

    @Bean
    public Map<String, Mount> trollMountMap() {
        Map<String, Mount> mapOfMounts = new HashMap<>();
        mapOfMounts.put("m1", frostWolf());
        mapOfMounts.put("m2", shadowTiger());
        return mapOfMounts;
    }

    @Bean
    public Unit zulJin() {
        Troll troll = new Troll();
        troll.setColorCode(ThreadLocalRandom.current().nextInt(1,10));
        try {
            troll.setCreationDate(dateFormatter().parse(characterCreatedDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Mount> listOfMounts = new LinkedList<>();
        listOfMounts.add(troll.DEFAULT_MOUNT);
        listOfMounts.add(null);
        listOfMounts.add(shadowTiger());
        troll.setListOfMounts(listOfMounts);

        troll.setSetOfMounts(trollMountSet());
        troll.setMapOfMounts(trollMountMap());

        return troll;
    }
}
