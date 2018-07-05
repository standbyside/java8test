package examples;

import entity.DisplayFeatures;
import entity.Mobile;
import entity.ScreenResolution;

import java.util.Optional;

/**
 * @author zhaona
 * @create 2018/5/18 14:51
 */
public class OptionalExamples {

    /**
     * 【Optional Basic example】
     *
     * Optional.ofNullable() method returns a Non-empty Optional if a value present in the given object. Otherwise returns empty Optional.
     * Optionaal.empty() method is useful to create an empty Optional object.
     */
    public static void example1() {
        Optional<String> gender = Optional.of("MALE");
        String answer1 = "Yes";
        String answer2 = null;

        // Optional[MALE]
        System.out.println(gender);
        // MALE
        System.out.println(gender.get());
        // Optional.empty
        System.out.println(Optional.empty());

        // Optional[Yes]
        System.out.println(Optional.ofNullable(answer1));
        // Optional.empty
        System.out.println(Optional.ofNullable(answer2));
        // java.lang.NullPointerException
        System.out.println(Optional.of(answer2));
    }

    /**
     * 【Optional.map and flatMap】
     */
    public static void example2() {
        Optional<String> nonEmptyGender = Optional.of("male");
        Optional<String> emptyGender = Optional.empty();

        // Optional[MALE]
        System.out.println(nonEmptyGender.map(String::toUpperCase));
        // Optional.empty
        System.out.println(emptyGender.map(String::toUpperCase));

        Optional<Optional<String>> nonEmptyOtionalGender = Optional.of(Optional.of("male"));
        // Optional[Optional[male]]
        System.out.println(nonEmptyOtionalGender);
        // Optional[Optional[MALE]]
        System.out.println(nonEmptyOtionalGender.map(gender -> gender.map(String::toUpperCase)));
        // Optional[MALE]
        System.out.println(nonEmptyOtionalGender.flatMap(gender -> gender.map(String::toUpperCase)));
    }

    /**
     * 【Optional.filter】
     */
    public static void example3() {
        Optional<String> gender = Optional.of("MALE");
        Optional<String> emptyGender = Optional.empty();

        // Optional.empty
        System.out.println(gender.filter(g -> g.equals("male")));
        // Optional[MALE]
        System.out.println(gender.filter(g -> g.equalsIgnoreCase("MALE")));
        // Optional.empty
        System.out.println(emptyGender.filter(g -> g.equalsIgnoreCase("MALE")));
    }

    /**
     * 【Optional isPresent and ifPresent】
     *
     * Optional.isPresent() returns true if the given Optional object is non-empty. Otherwise it returns false.
     * Optional.ifPresent() performs given action if the given Optional object is non-empty. Otherwise it returns false.
     */
    public static void example4() {
        Optional<String> gender = Optional.of("MALE");
        Optional<String> emptyGender = Optional.empty();

        if (gender.isPresent()) {
            System.out.println("Value available.");
        } else {
            System.out.println("Value not available.");
        }

        gender.ifPresent(g -> System.out.println("In gender Option, value available."));
        emptyGender.ifPresent(g -> System.out.println("In emptyGender Option, value available."));
    }

    /**
     * 【Optional orElse methods】
     *
     * It returns the value if present in Optional Container. Otherwise returns given default value.
     */
    public static void example5() {
        Optional<String> gender = Optional.of("MALE");
        Optional<String> emptyGender = Optional.empty();

        // MALE
        System.out.println(gender.orElse("<N/A>"));
        // <N/A>
        System.out.println(emptyGender.orElse("<N/A>"));

        // MALE
        System.out.println(gender.orElseGet(() -> "<N/A>"));
        // <N/A>
        System.out.println(emptyGender.orElseGet(() -> "<N/A>"));
    }

    public static void example6() {
        ScreenResolution resolution = new ScreenResolution(750,1334);
        DisplayFeatures dfeatures = new DisplayFeatures("4.7", Optional.of(resolution));
        Mobile mobile = new Mobile(2015001, "Apple", "iPhone 6s", Optional.of(dfeatures));


        int width = getMobileScreenWidth(Optional.of(mobile));
        System.out.println("Apple iPhone 6s Screen Width = " + width);

        Mobile mobile2 = new Mobile(2015001, "Apple", "iPhone 6s", Optional.empty());
        int width2 = getMobileScreenWidth(Optional.of(mobile2));
        System.out.println("Apple iPhone 6s Screen Width = " + width2);
    }

    /**
     * Here we can observe that how clean our getMobileScreenWidth() API without null checks and boiler plate code.
     * We don not worry about NullPointerExceptions at run-time.
     * <url>https://stackoverflow.com/questions/26327957/should-java-8-getters-return-optional-type/26328555#26328555</url>
     * @param mobile
     * @return
     */
    private static Integer getMobileScreenWidth(Optional<Mobile> mobile) {
        return mobile.flatMap(Mobile::getDisplayFeatures)
                .flatMap(DisplayFeatures::getResolution)
                .map(ScreenResolution::getWidth)
                .orElse(0);
    }
}
