//package nl.backend.eindoprdracht.repositories.Specifications;
//
//import org.springframework.data.jpa.domain.Specification;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Predicate;
//
//// hiermee zoek je op alles vanuit een Superclasse
//public class GenericSpecifications {
//
//    private final Object criteria;
//    public GenericSpecifications(Object criteria) {
//        this.criteria = criteria;
//    }
//
//    public static Specification toPredicate(Object cirteria){
//        return (root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            for (Field field : criteria.getClasses().getDeclaredFields()){
//                try {
//                    Object value = field.get(critera)
//                }
//            }
//
//        }
//    }



