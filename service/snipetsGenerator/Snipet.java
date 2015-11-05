/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.snipetsGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cyril
 */
public class Snipet {
    public String url;
    public String title;
    public String content;
    public String icon;
    public List<Concept> concepts = new ArrayList<Concept>();
}
