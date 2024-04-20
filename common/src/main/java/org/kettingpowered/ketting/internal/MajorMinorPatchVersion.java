package org.kettingpowered.ketting.internal;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author C0D3 M4513R
 * @param major major version
 * @param minor minor version
 * @param patch patch version
 * @param <T> version type
 */
public record MajorMinorPatchVersion<T extends Comparable<T>>(T major, T minor, T patch, String other) implements Comparable<MajorMinorPatchVersion<T>> {
    public static MajorMinorPatchVersion<String> parse(String version){
        return parse(version, "\\.");
    }
    public static MajorMinorPatchVersion<String> parse(String version, String seperator){
        String major, minor, patch, other;
        String[] parts = version.split(seperator, 4);
        if (parts.length>=1) major=parts[0];
        else throw new IllegalStateException("Split returned an empty array");
        if (parts.length>=2) minor = parts[1];
        else minor=null;
        if (parts.length>=3) patch = parts[2];
        else patch=null;
        if (parts.length>=4) other = parts[3];
        else other = null;
        return new MajorMinorPatchVersion<>(major, minor, patch, other);
    }
    
    @Override
    public String toString(){
        String out = major.toString();
        if (minor!=null) out+="."+minor;
        if (patch!=null) out+="."+patch;
        if (other!=null && !other.isEmpty()) out+="."+other;
        return out;
    }

    @Override
    public int compareTo(@NotNull MajorMinorPatchVersion<T> o) {
        int mjc = major.compareTo(o.major);
        if (mjc!=0 || minor==null || o.minor == null) return mjc;
        int mnc = minor.compareTo(o.minor);
        if (mnc!=0 || patch==null || o.patch == null) return mnc;
        int mpc = patch.compareTo(o.patch);
        if (mpc!=0 || other==null || o.other == null) return mpc;
        return other.compareTo(o.other);
    }
    
    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        else if (o == this) return true; 
        else if (o instanceof MajorMinorPatchVersion<?> om){
            if (!Objects.equals(major, om.major)) return false;
            else if (!Objects.equals(minor, om.minor)) return false;
            else if (!Objects.equals(patch, om.patch)) return false;
            else return Objects.equals(other, om.other);
        }
        else return false;
    }

    public <O extends Comparable<O>> MajorMinorPatchVersion<O> convertMMP(Function<T,O> parser) {
        return new MajorMinorPatchVersion<>(
                parser.apply(major),
                parser.apply(minor),
                parser.apply(patch),
                other
        );
    }

    public static HashMap<MajorMinorPatchVersion<Integer>, List<Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>>>> parseKettingServerVersionList(Stream<String> versions){
        HashMap<MajorMinorPatchVersion<Integer>, List<Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>>>> map = versions.map(version -> MajorMinorPatchVersion.parse(version, "-"))
                .map(version -> new MajorMinorPatchVersion<>(
                        MajorMinorPatchVersion.parse(version.major),//mc version
                        MajorMinorPatchVersion.parse(version.minor),//forge version
                        MajorMinorPatchVersion.parse(version.patch),//Ketting version
                        version.other
                )).map(version->new MajorMinorPatchVersion<>(
                        new MajorMinorPatchVersion<>(Integer.parseInt(version.major().major()), Integer.parseInt(version.major().minor()), Integer.parseInt(version.major().patch()), null),
                        new MajorMinorPatchVersion<>(Integer.parseInt(version.minor().major()), Integer.parseInt(version.minor().minor()), Integer.parseInt(version.minor().patch()), null),
                        new MajorMinorPatchVersion<>(Integer.parseInt(version.patch().major()), Integer.parseInt(version.patch().minor()), Integer.parseInt(version.patch().patch()), null),
                        version.other
                ))
                .reduce(new HashMap<>(), (hm, mmp)->{
                    hm.computeIfAbsent(mmp.major(), (key)->new ArrayList<>()).add(new Tuple<>(mmp.minor(), mmp.patch()));
                    return hm;
                }, (hm1, hm2)->{
                    for(final Map.Entry<MajorMinorPatchVersion<Integer>, List<Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>>>> entry : hm2.entrySet()){
                        hm1.computeIfAbsent(entry.getKey(), (key)-> new ArrayList<>()).addAll(entry.getValue());
                    }
                    hm2.clear();
                    return hm1;
                });
        map.values().forEach(list->list.sort(Comparator.comparing((Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>> t) -> t.t1()).thenComparing(Tuple::t2).reversed()));
        return map;
    }
}