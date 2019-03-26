import controller.ItemController;
import model.Item;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = {"/add", "/delete", "/update", "/find", "/test", "/getItems"})

//(urlPatterns = "/add")
public class MyServlet extends HttpServlet {
    ItemController itemController = new ItemController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();

        switch (action) {
            case "/add":
                addItem(req, resp);
                break;

            case "/delete":
                delete(req, resp);
                break;
            case "/update":
                updateItem(req, resp);
                break;
            case "/find":
                find(req, resp);
                break;
            case "/test":
                resp.getWriter().println(req.getParameter("param"));
                break;
            case "/getItems":
                listItems(req, resp);
                break;
            default:
                listItems(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
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
        Item newItem = new Item();
        String name = req.getParameter("name");
        Date dateCreated = new Date();
        String description = req.getParameter("description");
        newItem.setName(name);
        newItem.setDateCreated(dateCreated);
        newItem.setLastUpdatedDate(dateCreated);
        newItem.setDescription(description);

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
        if (description != null) {
            updateItem.setDescription(description);
        }
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
