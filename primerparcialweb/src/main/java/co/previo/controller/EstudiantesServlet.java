package co.previo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.previo.modelo.Estudiantes;
import co.previo.dao.EstudiantesDAO;

/**
 * Servlet implementation class EstudiantesServlet
 */
@WebServlet("/")
public class EstudiantesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private EstudiantesDAO UsuarioDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EstudiantesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
		this.UsuarioDao = new EstudiantesDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
String action = request.getServletPath();
		
		try {
			switch(action) {
				case "/new":
					showNewForm(request, response);
					break;
				case "/insert":
					insertUser(request, response);
					break;
				case "/delete":
                    deleteUser(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateUser(request, response);
                    break;
				default: 
					listUser(request, response);
					break;
			}
		} catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		        dispatcher.forward(request, response);
		    }
	
	private void insertUser(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException, ParseException {
		        String documento = request.getParameter("documento");
		        String nombre = request.getParameter("nombre");
		        String apellido = request.getParameter("apellido");
		        String email = request.getParameter("email");
		        String genero = request.getParameter("genero");
		        Date fechamacimiento = new SimpleDateFormat("yyyy-MM-dd").parse("fechamacimiento");
		        String direccion = request.getParameter("direccion");
		        Float peso = Float.parseFloat(request.getParameter("peso"));
		        Float estatura = Float.parseFloat(request.getParameter("estatura"));
		        Estudiantes newUser = new  Estudiantes(null, documento, nombre, apellido, email, genero, fechamacimiento, direccion, direccion, peso, estatura);
		        UsuarioDao.insertUser(newUser);
		        response.sendRedirect("list");
		    }
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, ServletException, IOException {
		        int id = Integer.parseInt(request.getParameter("id"));
		        Estudiantes existingUser = UsuarioDao.selectUser(id);
		        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		        request.setAttribute("user", existingUser);
		        dispatcher.forward(request, response);

		    }
	
	private void listUser(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException, ServletException {
		        List < Estudiantes > listUser = UsuarioDao.selectAllUsers();
		        request.setAttribute("listUser", listUser);
		        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		        dispatcher.forward(request, response);
		    }
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException, ParseException {
		        int id = Integer.parseInt(request.getParameter("id"));
		        String documento = request.getParameter("documento");
		        String nombre = request.getParameter("nombre");
		        String apellido = request.getParameter("apellido");
		        String email = request.getParameter("email");
		        String genero = request.getParameter("genero");
		        Date fechamacimiento = new SimpleDateFormat("yyyy-MM-dd").parse("fechamacimiento");
		        String direccion = request.getParameter("direccion");
		        Float peso = Float.parseFloat(request.getParameter("peso"));
		        Float estatura = Float.parseFloat(request.getParameter("estatura"));
		        
		        Estudiantes book = new  Estudiantes(null, documento, nombre, apellido, email, genero, fechamacimiento, direccion, direccion, peso, estatura);
		        
		        UsuarioDao.updateUser(book);
		        response.sendRedirect("list");
		    }
	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException {
		        int id = Integer.parseInt(request.getParameter("id"));
		        UsuarioDao.deleteUser(id);
		        response.sendRedirect("list");

		    }

}
