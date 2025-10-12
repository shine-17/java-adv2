package was.httpserver.servlet.annotation;

import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;
import was.httpserver.HttpServlet;
import was.httpserver.PageNotFoundException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationServletV3 implements HttpServlet {

    private final Map<String, ControllerMethod> pathMap;

    public AnnotationServletV3(List<Object> controllers) {
        this.pathMap = new HashMap<>();
        initializePathMap(controllers);
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String path = request.getPath();
        ControllerMethod controllerMethod = pathMap.get(path);

        if (controllerMethod == null) {
            throw new PageNotFoundException("request= " + path);
        }

        controllerMethod.invoke(request, response);
    }

    private void initializePathMap(List<Object> controllers) {
        System.out.println("============= Init Handler Mapping =============");

        for (Object controller : controllers) {
            Method[] methods = controller.getClass().getDeclaredMethods();

            for (Method method : methods) {
                if (method.isAnnotationPresent(Mapping.class)) {
                    String path = method.getAnnotation(Mapping.class).value();

                    // 중복 경로 체크 todo
                    if (pathMap.containsKey(path)) {
                        ControllerMethod controllerMethod = pathMap.get(path);
                        throw new IllegalStateException("경로 중복 등록, path=" + path + ", method=" + method + ", 이미 등록된 메서드=" + controllerMethod.method);
                    }

                    pathMap.put(path, new ControllerMethod(controller, method));
                }
            }
        }

        pathMap.entrySet().forEach(m -> System.out.println("path=" + m.getKey() + ", method=" + m.getValue().method));

        System.out.println("============= Init Handler Mapping =============");
    }

    private static class ControllerMethod {
        private final Object controller;
        private final Method method;

        public ControllerMethod(Object controller, Method method) {
            this.controller = controller;
            this.method = method;
        }

        public void invoke(HttpRequest request, HttpResponse response) {
            try {
                Class<?>[] parameterTypes = method.getParameterTypes();
                // request, response
                Object[] args = new Object[parameterTypes.length];
                for (int i=0; i<parameterTypes.length; i++) {
                    if (parameterTypes[i] == HttpRequest.class) {
                        args[i] = request;
                    }
                    else if (parameterTypes[i] == HttpResponse.class) {
                        args[i] = response;
                    }
                    else {
                        throw new IllegalArgumentException("Unsupported parameter type: " + parameterTypes[i]);
                    }
                }

                method.invoke(controller, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
