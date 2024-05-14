import java.util.function.Function;
import java.util.stream.Stream;

class TextProcessor{
    private Function<String , String> transformation;
    @SuppressWarnings("unchecked")
    TextProcessor(Function<String , String>... transformation){
        this.transformation = Stream.of(transformation).reduce(Function.identity(), Function::andThen);
    }

    String process(String input){
        return transformation.apply(input);
    }
}