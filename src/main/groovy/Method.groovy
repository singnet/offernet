package io.singularitynet.offernet

public class Method {
        private final String name;
        private final List<Object> args;

        public Method(String name, List<Object> args) {
                this.name = name;
                this.args = args;
        }
        
        @Override
        public String toString() {
                return this.name+"( "+args.toString() +" ) ";
        }
}
