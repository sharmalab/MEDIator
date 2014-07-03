package edu.emory.bmi.datarepl.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(final HttpServletRequest req,
			final HttpServletResponse res) throws ServletException, IOException {
		res.getWriter()
				.append("  <HTML>\n" +
                        "    <HEAD>\n" +
                        "      <TITLE>Search Series</TITLE>\n" +
                        "    </HEAD>\n" +
                        "\n" +
                        "\n" +
                        "    <BODY>\n" +
                        "<CENTER>\n" +
                        "       <form action=\"series.html\">\n" +
                        "  Patient: <input type=\"text\" name=\"iPatientID\"><br>\n" +
                        "  Collection: <input type=\"text\" name=\"iCollection\"><br>\n" +
                        "  Modality: <input type=\"text\" name=\"iModality\"><br>\n" +
                        "  Body Part: <input type=\"text\" name=\"iBodyPartExamined\"><br>\n" +
                        "  <input type=\"submit\" value=\"Search (getSeries)\">\n" +
                        "</form> \n" +
                        "</CENTER>\n" +
                        "\n" +
                        "    </BODY>\n" +
                        "  </HTML>\n" +
                        "\n");
	}
}
