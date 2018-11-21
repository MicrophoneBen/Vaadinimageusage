package com.ghostben.test;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.*;
import com.vaadin.ui.*;

import java.awt.font.ImageGraphicAttribute;
import java.io.File;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });

        //加载静态资源文件
        // Find the application directory
        String basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath();

        // Image as a file resource
        FileResource resource = new FileResource(new File(basepath +
                "/WEB-INF/images/logo.png"));

        // Show the image in the application
        Image image = new Image("Image from file", resource);

        // Let the user view the file in browser or download it
        Link link = new Link("Link to the image file", resource);
    // 加载静态资源文件 ends

        //加载类路径下资源文件
        VerticalLayout verLayout = new VerticalLayout();
        verLayout.addComponent(new Image("类路径下加载静态资源文件",
                new ClassResource("/WEB-INF/classes/logo.png")));
       //类路径下加载静态资源文件


        //主题文件路径下访问静态资源文件
        // A theme resource in the current theme ("mytheme")
        // Located in: VAADIN/themes/mytheme/img/themeimage.png
        ThemeResource themeResource = new ThemeResource("img/logo.png");

        // Use the resource
        Image theme_image = new Image("My Theme Image", themeResource);
        //主题文件路径下访问静态资源文件


        //使用我们自己写的动态图片加载类来加载图片文件
        // Create an instance of our stream source.
                StreamResource.StreamSource imagesource = new MyImageSource();

        // Create a resource that uses the stream source and give it
        // a name. The constructor will automatically register the
        // resource in the application.
        StreamResource myOwnResource =
                new StreamResource(imagesource, "myCreateimage.png");

        // Create an image component that gets its contents
        // from the resource.
        Image image1 = new Image("我的资源文件",myOwnResource);


        //Image logoImage = new Image("公司标识",new ClassResource("/WEB-INF/images/logo.png"));
        layout.addComponents(name, button,image,link,verLayout,theme_image,image1);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
