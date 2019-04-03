import controller.ItemController;
import controller.JsonUtil;
import model.Item;
import org.apache.commons.io.IOUtils;


import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = {"/getItems", "/findById", "/add", "/update", "/delete"})

//(urlPatterns = "/add")
///add", "/delete", "/update", "/find", "/test", "/getItems"}
public class MyServlet extends HttpServlet {
    ItemController itemController = new ItemController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //listItems(req, resp);
        String strId = req.getParameter("id").trim();
        long id = Long.parseLong(strId);
        Item item = itemController.findById(id);
        resp.getWriter().println(item);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addItem(req, resp);
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
