import controller.ItemController;
import controller.JsonUtil;
import model.Item;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = {"/getItems", "/findById", "/add", "/update", "/delete"})

//(urlPatterns = "/add")
///add", "/delete", "/update", "/find", "/test", "/getItems"}
public class MyServlet extends HttpServlet {
    ItemController itemController = new ItemController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String strId = req.getParameter("id").trim();
        long id = Long.parseLong(strId);
        Item item = itemController.findById(id);
        resp.getWriter().println(item);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        objectMapper.setDateFormat(df);

        String str = new String();
        try {
            str = IOUtils.toString(req.getInputStream());
        } catch (IOException e) {
            System.out.println("Exception occured while servlet input stream reading" + e.getMessage());
        }
        try {
            Item newItem = objectMapper.readValue(str, Item.class);
            itemController.add(newItem);

        } catch (JsonParseException e) {
            System.out.println("Exception Occured whiile converting the Json into java" + e.getMessage());
        } catch (JsonMappingException e) {
            System.out.println("Exception Occured whiile converting the Json into java" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception Occured whiile converting the Json into java" + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        updateItem(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        delete(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }


    @Override
    public void destroy() {
        super.destroy();
    }


    @Override
    public void init() throws ServletException {
        super.init();
    }

    //servlet registration - init()
    //servlet works with service method
    //to close servlet with its resources - destroy()

    //HTTP REQUESTS
    //GET - get some info
    //POST - save some info. мы хотим что-бы сервер сохрнил какие-то данные в базе данных сервера
    //PUT - update (обновляет данные)
    //DELETE - delete info

    //CRUD - create read update delete


    private void addItem(HttpServletRequest req, HttpServletResponse resp) {

        String str = new String();
        try {
            str = IOUtils.toString(req.getInputStream());
        } catch (IOException e) {
            System.out.println("Exception occured while servlet input stream reading" + e.getMessage());
        }
        Item newItem = JsonUtil.convertJsonToJava(str, Item.class);
        itemController.add(newItem);
    }

    private void updateItem(HttpServletRequest req, HttpServletResponse resp) {

        String strId = req.getParameter("id").trim();
        long id = Long.parseLong(strId);
        Item updateItem = itemController.findById(id);

        String name = req.getParameter("name");
        if (name != null) {
            updateItem.setName(name);
        }
        Date dateUpdated = new Date();
        updateItem.setLastUpdatedDate(dateUpdated);

        String description = req.getParameter("description");
        updateItem.setDescription(description);

        itemController.update(updateItem);
    }


    private void find(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String strId = req.getParameter("id").trim();
        long id = Long.parseLong(strId);
        Item item = itemController.findById(id);
        resp.getWriter().println(item);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String strId = req.getParameter("id").trim();
        long id = Long.parseLong(strId);
        Item item = itemController.delete(id);

    }

    private void listItems(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Item> listItems = itemController.itemsList();
        resp.getWriter().println(listItems);
    }

}
