import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Recipes {

    private static final int UNKNOWN = 0;
    private static final int COOKING = 1;
    private static final int COOKED = 2;


    public static void main(String[] args) {
        String[] rec = {"bread"};
        List<List<String>> ing = new ArrayList<>();
        ing.add(List.of("yeast", "floor"));
        String[] sup = {"yeast","floor","corn"};

        System.out.println(findAllRecipes(rec, ing, sup));
        System.out.println(findAllRecipes_Short(rec, ing, sup));

    }

    public static List<String> findAllRecipes_Short(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        Set<String> supple=new HashSet<>(Arrays.asList(supplies));
        Set<String> ans=new HashSet<>();

        for(int i=0;i<ingredients.size();i++){
            outer:
            for(int j=0;j<ingredients.size();j++){
                if(ans.contains(recipes[j]))continue;//recipe has already been made
                for(String ingre:ingredients.get(j)){
                    if(!supple.contains(ingre))continue outer;//As all ingredients are not present in supplies
                }
                ans.add(recipes[j]);// add it to the answer
                supple.add(recipes[j]); //add it to the supplies list so that another recipe can use this recipe
            }

        }
        return new ArrayList<>(ans);
    }

    public static List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        Map<String, Integer> status = new HashMap<>();
        Map<String, List<String>> prereqs = new HashMap<>();

        for (int i = 0; i < recipes.length; ++ i) {
            status.put(recipes[i], UNKNOWN);
            prereqs.put(recipes[i], ingredients.get(i));
        }

        for (String s: supplies) {
            status.put(s, COOKED);
        }

        List<String> output = new ArrayList<>();
        for (String currentRecipe: recipes) {
            dfsToStartCooking(currentRecipe, prereqs, status, output);
        }

        return output;
    }

    public static boolean dfsToStartCooking(String currentRecipe, Map<String, List<String>> prereqs, Map<String, Integer> status, List<String> output) {
        if (!status.containsKey(currentRecipe)) {  // an ingredient not possible to cook or missing in supplies list
            return false;
        }

        if (status.get(currentRecipe) == COOKING) { // circular loop ==> A => B => C => A
            return false;
        }

        if (status.get(currentRecipe) == COOKED) { // Memo: Already cooked before and must be added to output list.
            return true;
        }

        status.put(currentRecipe, COOKING); //Start cooking current recipe or its ingredient.
        for (String p: prereqs.get(currentRecipe)) {
            if (!dfsToStartCooking(p, prereqs, status, output)) {
                return false; // Not possible to cook currentRecipe
            }
        }
        status.put(currentRecipe, COOKED); //Add the current recipe to supplies list, so that other dependent recipe can take this
        output.add(currentRecipe);

        return true;
    }
}
