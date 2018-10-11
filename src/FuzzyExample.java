import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;


public class FuzzyExample {
    static boolean first = true;
    public static void main(String[] args) throws Exception {
        FuzzyExample.evaluate(70, -2, 12);
    }
   public static double evaluate(int temperatura, int preferencja, int godzina) throws Exception {
        try {

            final FIS fis = FIS.load("out\\production\\MSI_sterownik\\fuzzy_volume.fcl", false);

            final FuzzyRuleSet fuzzyRuleSet = fis.getFuzzyRuleSet();
            if(first) {
                fuzzyRuleSet.chart();
                first = false;
            }

            fuzzyRuleSet.setVariable("temperatura", temperatura);
            fuzzyRuleSet.setVariable("preferencja", preferencja);
            fuzzyRuleSet.setVariable("godzina", godzina);

            fuzzyRuleSet.evaluate();
            if(first) {
                fuzzyRuleSet.getVariable("akcja").chartDefuzzifier(true);
                first = false;
            }
            
           return fuzzyRuleSet.getVariable("akcja").getLatestDefuzzifiedValue();
            //System.out.println(fuzzyRuleSet);
        }
        catch(final ArrayIndexOutOfBoundsException ex) {
            System.out.println(
                    "Niepoprawna liczba parametrow. Przyklad: java FuzzyExample string<plik_fcl> int<temperatura> int<preferencja> int<godzina>");
        }
        catch(final NumberFormatException ex) {
            System.out.println(
                    "Niepoprawny parametr. Przyklad: java FuzzyExample string<plik_fcl> int<temperatura> int<preferencja> int<godzina>");
        }
        catch(Exception ex) {
            System.out.println(ex.toString());
        }
       return 0;
    }
}