package com.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Date;
import java.util.Scanner;

public class Library_v2 {

	static Scanner scan;
	static Connection conn;
	static Statement stmt;
	static PreparedStatement pstmt;

	public static void main(String[] args) {
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:pdborcl", "library", "library");
			scan = new Scanner(System.in);
			System.out.println("LIBRARY MANAGEMENT SYSTEM\n");
			char ch;
			do {
				System.out.println("----------------------");
				System.out.println("Library Operations");
				System.out.println("----------------------");
				System.out.println("1. Admin Options");
				System.out.println("2. Reader Options");
				System.out.println("3. Quit");
				System.out.println("----------------------");
				System.out.print(">>");
				int choice = scan.nextInt();
				switch (choice) {
				case 1:
					adminLogin();
					break;
				case 2:
					readerLogin();
					break;
				case 3:
					System.out.println("Thank You. Visit Again!");
					System.exit(0);
					break;
				default:
					System.out.println("Wrong Entry. Please Enter 1, 2 Or 3. \n ");
					break;
				}

				System.out.println("\nDo you want to continue Library Transactions ? (Type y or n) \n");
				ch = scan.next().charAt(0);

			} while (ch == 'Y' || ch == 'y');

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void readerLogin() {
		// TODO Auto-generated method stub
		int bid = 0;
		try {
			System.out.println("Enter Your Reader ID : ");
			int rid = scan.nextInt();
			String query = "SELECT COUNT(*) FROM READER WHERE READERID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, rid);
			ResultSet rs = pstmt.executeQuery();
			
			String query1 = "SELECT BRANCHID FROM REGISTER WHERE READERID = ?";
			pstmt = conn.prepareStatement(query1);
			pstmt.setInt(1, rid);
			ResultSet rs1 = pstmt.executeQuery();
			while(rs1.next()){
				bid = rs1.getInt(1);
			}
			
			
			if(rs.next()){
				int a = rs.getInt(1);
				if(a>0){
					System.out.println("-----------------------");
					System.out.println("Reader Functions Menu");
					System.out.println("-----------------------");
					char rch = 'n';
					do {
						// System.out.println("Reader Operations\n");
						System.out.println("1. Search a document.");
						System.out.println("2. Document checkout.");
						System.out.println("3. Document Return.");
						System.out.println("4. Document Reserve.");
						System.out.println("5. Compute Fine.");
						System.out.println("6. List of Documents Reserved.");
						System.out.println("7. Books by a Publisher.");
						System.out.println("8. Quit.");
						int rchoice = scan.nextInt();
						switch (rchoice) {
						case 1:
							//System.out.println("Query 1");
							adminSearchDocument();
							break;
						case 2:
							//System.out.println("Query 2");
							readerDocumentCheckOut(rid, bid);
							break;
						case 3:
							//System.out.println("Query 3");
							readerDocumentReturn(rid, bid);
							break;
						case 4:
							//System.out.println("Query 4");
							readerDocumentReserve(rid, bid);
							break;
						case 5:
							//System.out.println("Query 5");
							computeFine(rid);
							break;
						case 6:
							//System.out.println("Query 6");
							listOfDocumentsReserved(rid);
							break;
						case 7:
							//System.out.println("Query 7");
							listOfDocumentsByPublisher();
							break;
						case 8:
							System.out.println("Thank You. Visit Again!");
							System.exit(0);
							break;
						}
						System.out.println("\nDo you want to continue Reader Operations ? (Type y or n) \n");
						rch = scan.next().charAt(0);
					} while (rch == 'Y' || rch == 'y');

				}else{
					System.out.println("Reader Not Found.");
				}
			}			
			/*if (!rs.next()) {
				System.out.println("Reader Not Found.");
			}*/ 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void adminLogin() {
		// TODO Auto-generated method stub

		try {
			System.out.println("Enter Your UserId : ");
			String uid = scan.next();
			System.out.println("Enter Your Password : ");
			String pwd = scan.next();
			stmt = conn.createStatement();
			String query = "SELECT USERNAME,PASSWORD FROM LOGIN WHERE USERNAME = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uid);
			ResultSet rs = pstmt.executeQuery();
			// If entry found/available in table but incorrect userid/pwd
			// combination
			if (rs.next()) {
				String user = rs.getString(1);
				String pass = rs.getString(2);
				if (user.equalsIgnoreCase(uid) && pass.equals(pwd)) {
					// System.out.println("Here");
					System.out.println("----------------------------------");
					System.out.println("Administrative Functions Menu");
					System.out.println("----------------------------------");
					char ach = 'n';

					do {
						// System.out.println("Administrative Operations\n");
						System.out.println("1. Add Document");
						System.out.println("2. Add Document Copy");
						System.out.println("3. Search Document ");
						System.out.println("4. Add New Reader");
						System.out.println("5. Branch Information");
						System.out.println("6. Top 10 Borrowers");
						System.out.println("7. Top 10 Borrowed Books");
						System.out.println("8. Top 10 Popular Books of the year");
						System.out.println("9. Average Fine Per Reader");
						System.out.println("10. Quit.");
						int rchoice = scan.nextInt();
						switch (rchoice) {
						case 1:
							//System.out.println("Query 1");
							adminAddDocument();
							break;
						case 2:
							//System.out.println("Query 1");
							adminAddDocumentCopy();
							break;
						case 3:
							//System.out.println("Query 2");
							adminSearchDocument();
							break;
						case 4:
							//System.out.println("Query 3");
							adminAddReader();
							break;
						case 5:
							//System.out.println("Query 4");
							adminBranchInfo();
							break;
						case 6:
							//System.out.println("Query 5");
							adminTopReaders();
							break;
						case 7:
							//System.out.println("Query 6");
							adminTopBorrowedBooks();
							break;
						case 8:
							//System.out.println("Query 7");
							adminTopBooksOfYear();
							break;
						case 9:
							//System.out.println("Query 8");
							avgFine();
							break;
						case 10:
							System.out.println("Thank You. Visit Again!");
							System.exit(0);
							break;
						}
						System.out.println("\nDo you want to continue Admin Operations ? (Type y or n) \n");
						ach = scan.next().charAt(0);
					} while (ach == 'Y' || ach == 'y');
				} else {
					System.out.println("Wrong");
				}
			}
			// If entry not found/available in table rs will be empty
			if (!rs.next()) {
				System.out.println("Invalid UserId and Password.");
			}
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void adminAddDocumentCopy() {
		// TODO Auto-generated method stub
		try {
			System.out.print("\nEnter Document Details.");
			System.out.print("\nEnter Document Id : ");
			int docId = scan.nextInt();
			System.out.print("\nEnter Branch Id : ");
			int brId = scan.nextInt();
			String query = "SELECT * FROM DOCUMENT WHERE DOCUMENTID=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, docId);
			int rs = pstmt.executeUpdate();
			if (rs != -1) {
				String query1 = "SELECT MAX(COPYID) FROM DOCUMENTCOPY WHERE DOCUMENTID=?";
				pstmt = conn.prepareStatement(query1);
				pstmt.setInt(1, docId);
				ResultSet rs1 = pstmt.executeQuery();
				int copyid = 0;
				if (rs1.next()) {
					copyid = rs1.getInt(1);
				} else {
					copyid = 0;
				}
				String query2 = "INSERT INTO DOCUMENTCOPY VALUES(?,?,?,?)";
				pstmt = conn.prepareStatement(query2);
				pstmt.setInt(1, docId);
				pstmt.setInt(2, ++copyid);
				pstmt.setInt(3, brId);
				pstmt.setInt(4, 0);
				// System.out.println(query2);
				int rs2 = pstmt.executeUpdate();
				if (rs2 != -1) {
					System.out.println("Insertion Successful.");
				} else {
					System.out.println("Insertion Failed.");
				}
			} else {
				System.out.println("Insertion Failed.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void listOfDocumentsByPublisher() {
		// TODO Auto-generated method stub

		try {
			System.out.println("Enter Publisher Id : ");
			int pid = scan.nextInt();
			String query = "SELECT D.DOCUMENTID,D.TITLE,P.PUBNAME FROM DOCUMENT D,PUBLISHER P WHERE P.PUBLISHERID=D.PUBLISHERID AND P.PUBLISHERID=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pid);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("DOCUMENT_ID\tTITLE\tPUBLISHER NAME");
			System.out.println("----------------------------------");
			while (rs.next()) {
				int did = rs.getInt(1);
				String title = rs.getString(2);
				String pname = rs.getString(3);
				System.out.println(did + "\t" + title + "\t" + pname);
			}
			System.out.println("----------------------------------");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void listOfDocumentsReserved(int rid) {
		// TODO Auto-generated method stub

		try {
			// System.out.println("Enter Your Id : ");
			// int rid = scan.nextInt();
			String query = "SELECT D.DOCUMENTID,D.STATUS FROM RESERVES R,DOCUMENTCOPY D WHERE D.DOCUMENTID=R.DOCUMENTID AND D.COPYID=R.COPYID AND R.READERID=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, rid);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("DOCUMENT_ID\tSTATUS");
			System.out.println("----------------------------------");
			while (rs.next()) {
				int did = rs.getInt(1);
				int status = rs.getInt(2);
				if (status == 1) {
					System.out.println(did + "\t\tBORROWED");
				} else if (status == 0) {
					System.out.println(did + "\t\tAVAILABLE");
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void computeFine(int rid) {
		// TODO Auto-generated method stub

		try {
			// System.out.println("Enter Your Id : ");
			// int rid = scan.nextInt();
			System.out.println("Enter Document Id : ");
			int docid = scan.nextInt();
			System.out.println("Enter Copy Id : ");
			int cid = scan.nextInt();
			String query = "SELECT FINE FROM BORROWS WHERE DOCUMENTID = ? AND COPYID = ? AND READERID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, docid);
			pstmt.setInt(2, cid);
			pstmt.setInt(3, rid);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("DOCUMENT ID\tCOPYID\t\tFINE");
			System.out.println("---------------------------------------");
			while (rs.next()) {
				int fine = rs.getInt(1);
				System.out.println(docid + "\t\t" + cid + "\t\t" + fine);
			}
			System.out.println("----------------------------------------");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void readerDocumentReserve(int rid, int bid) {
		// TODO Auto-generated method stub

		try {
			
			String queryres = "select count(*) from reserves where readerid = ?";
			pstmt = conn.prepareStatement(queryres);
			pstmt.setInt(1, rid);
			ResultSet rschk = pstmt.executeQuery();
			rschk.next();
			int rescnt = rschk.getInt(1);
				if(rescnt>=3){
					System.out.println("You Cannot Reserve More Than 3 Books.");
				}else{
					System.out.println("Provide Required Details.");
					int cid = 0;			
					System.out.println("Enter Document Id : ");
					int docid = scan.nextInt();
					String query = "SELECT COPYID, BRANCHID FROM DOCUMENTCOPY WHERE DOCUMENTID = ? AND STATUS = 0";
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, docid);
					ResultSet rs = pstmt.executeQuery();
					if (!rs.next()) {
						System.out.println("No copies available.");
					} else {
						cid = rs.getInt(1);
						bid = rs.getInt(2);
						String query1 = "INSERT INTO RESERVES VALUES(" + rid + "," + docid + "," + cid + "," + bid
								+ ",SYSDATE)";
						pstmt = conn.prepareStatement(query1);
						int rs1 = pstmt.executeUpdate();
						if (rs1 != -1) {
							System.out.println("Document Reserved.");
						} else {
							System.out.println("Document Reservation Failed.");
						}
					}
				}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void readerDocumentReturn(int rid, int bid) {
		// TODO Auto-generated method stub

		try {
			// System.out.println("Enter Your Id : ");
			// int rid = scan.nextInt();
			System.out.println("Enter Document Id : ");
			int docid = scan.nextInt();
			System.out.println("Enter Copy Id : ");
			int cid = scan.nextInt();
			String query = "UPDATE DOCUMENTCOPY SET STATUS = 0 WHERE DOCUMENTID = ? AND COPYID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, docid);
			pstmt.setInt(2, cid);
			int rs = pstmt.executeUpdate();
			if (rs != -1) {
				String query1 = "UPDATE BORROWS SET FINE = (SYSDATE-RDTIME)*20 WHERE DOCUMENTID = ? AND COPYID = ? AND READERID = ? AND BRANCHID= ?";
				pstmt = conn.prepareStatement(query1);
				pstmt.setInt(1, docid);
				pstmt.setInt(2, cid);
				pstmt.setInt(3, rid);
				pstmt.setInt(4, bid);
				int rs1 = pstmt.executeUpdate();
				if (rs1 != -1) {
					System.out.println("Checking Fine. Please Wait...");
					String query2 = "SELECT FINE FROM BORROWS WHERE DOCUMENTID = ? AND COPYID = ?";
					pstmt = conn.prepareStatement(query2);
					pstmt.setInt(1, docid);
					pstmt.setInt(2, cid);
					ResultSet rs2 = pstmt.executeQuery();
					System.out.println("FINE");
					System.out.println("--------");
					while (rs2.next()) {
						int fine = rs2.getInt(1);
						if (fine > 0) {
							System.out.println(fine);
						} else {
							System.out.println("0");
						}
					}
				} else {
					System.out.println("Borrow Update Failed.");

				}

				// System.out.println("Document Copy Status Updated.");
			} else {
				System.out.println("Document Copy Status Update Failed.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void readerDocumentCheckOut(int rid, int bid) {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			
			String querycheck = "select COUNT(*) from documentcopy d,borrows b 	where d.documentid=b.documentid and d.copyid=b.copyid and d.status=1 group by b.readerid having b.readerid=?";
			pstmt = conn.prepareStatement(querycheck);
			pstmt.setInt(1, rid);
			ResultSet rschk = pstmt.executeQuery();
			while(rschk.next()){
				int bookcnt = rschk.getInt(1);
				if(bookcnt>=10){
					System.out.println("You Cannot Borrow More Than 10 Books.");
				}else{
					System.out.println("Provide Required Details.");
				}
			}
			
			System.out.println("Enter Document Id : ");
			int docid = scan.nextInt();
			String query = "SELECT COUNT(COPYID) FROM DOCUMENTCOPY WHERE DOCUMENTID=? AND STATUS=0";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, docid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			if (count > 0) {
				// System.out.println("Enter Reader Id : ");
				// int rid = scan.nextInt();
				String borrow1 = "INSERT INTO BORROWS VALUES(" + rid + "," + docid + "," + count
						+ ",124,SYSDATE,(SYSDATE+15),0)";
				String borrow2 = "UPDATE DOCUMENTCOPY SET STATUS=1 WHERE DOCUMENTID =" + docid + " AND COPYID = "
						+ count;
				// System.out.println(borrow1);
				// System.out.println(borrow2);
				pstmt = conn.prepareStatement(borrow1);
				int rs1 = pstmt.executeUpdate();
				pstmt = conn.prepareStatement(borrow2);
				int rs2 = pstmt.executeUpdate();
				if ((rs1 != -1)) {
					if (rs2 != -1) {
						System.out.println("Check Out Successful.");
					} else {
						System.out.println("Check Out Failed.");
						stmt.executeUpdate("ROLLBACK");
					}
				} else {
					System.out.println("Check Out Failed.");
				}
			} else {
				System.out.println("No books available.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void avgFine() {
		// TODO Auto-generated method stub
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT READERID,AVG(FINE) FROM BORROWS GROUP BY READERID");
			System.out.println("READER_ID	  	AVERAGE FINE");
			System.out.println("---------------------------------");
			while (rs.next()) {
				int rid = rs.getInt(1);
				int fine = rs.getInt(2);
				System.out.println(rid + "				" + fine);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void adminTopBooksOfYear() {
		// TODO Auto-generated method stub
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM (SELECT DOCUMENTID,COUNT(COPYID) FROM BORROWS GROUP BY DOCUMENTID ORDER BY COUNT(COPYID) DESC) WHERE ROWNUM<=10");
			System.out.println("DOCUMENT_ID			NUMBER_OF_TIMES_BORROWED");
			System.out.println("-----------------------------------------------");
			while (rs.next()) {
				int did = rs.getInt(1);
				int count = rs.getInt(2);
				System.out.println(did + "				" + count);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void adminTopBorrowedBooks() {
		// TODO Auto-generated method stub
		try {
			System.out.println("Enter Branch Id : ");
			int brid = scan.nextInt();
			String query = "SELECT * FROM(SELECT DOCUMENTID,COUNT(COPYID) FROM BORROWS WHERE BRANCHID = ? GROUP BY DOCUMENTID ORDER BY COUNT(COPYID) DESC) WHERE ROWNUM <=10";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, brid);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("DOCUMENT_ID			NUMBER_OF_COPIES");
			System.out.println("--------------------------------------");
			while (rs.next()) {
				int rid = rs.getInt(1);
				int count = rs.getInt(2);
				System.out.println(rid + "				" + count);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void adminTopReaders() {
		// TODO Auto-generated method stub

		try {
			System.out.println("Enter Branch Id : ");
			int brid = scan.nextInt();
			String query = "SELECT * FROM (SELECT READERID,COUNT(COPYID) FROM BORROWS WHERE BRANCHID =? GROUP BY READERID ORDER BY COUNT(COPYID) desc) WHERE ROWNUM<=10";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, brid);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("READER_ID		NUMBER_OF_COPIES");
			System.out.println("------------------------------------");
			while (rs.next()) {
				int rid = rs.getInt(1);
				int count = rs.getInt(2);
				System.out.println(rid + "				" + count);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void adminBranchInfo() {
		// TODO Auto-generated method stub

		try {
			System.out.println("Enter Branch Id : ");
			int brid = scan.nextInt();
			String query = "SELECT BRANCHNAME,LOCATION FROM BRANCH WHERE BRANCHID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, brid);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("BRANCH_NAME\t\t\t\t\t\t\tBRANCH_LOCATION");
			System.out.println("-----------------------------------------------------------------------------------------");
			while (rs.next()) {
				String brname = rs.getString(1);
				String loc = rs.getString(2);
				System.out.println(brname + "			" + loc);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void adminAddReader() {
		// TODO Auto-generated method stub
		int rid = 0;
		try {
			String query1 = "SELECT MAX(READERID)FROM READER";
			pstmt = conn.prepareStatement(query1);
			ResultSet rs2 = pstmt.executeQuery();
			while (rs2.next()) {
				rid = rs2.getInt(1);
			}
			rid++;
			// System.out.print("\nEnter Reader Id : ");
			// int rid = scan.nextInt();
			System.out.print("\nEnter Branch Id : ");
			int bid = scan.nextInt();
			System.out.print("\nEnter Reader Type (Senior, Staff or Student): ");
			String rtype = scan.next();
			System.out.print("\nEnter Reader Name : ");
			String rname = scan.next();
			System.out.print("\nEnter Reader Address : ");
			String addr = scan.next();
			System.out.print("\nEnter Reader Date of Birth (DD-MMM-YYYY): ");
			String dob = scan.next();
			// Date date = new Date(dob);
			System.out.print("\nEnter Reader Sex : ");
			String sex = scan.next();
			System.out.print("\nEnter Reader Phone : ");
			long phone = scan.nextLong();
			System.out.print("\nEnter Reader Email : ");
			String email = scan.next();

			String query = "INSERT INTO READER VALUES(?,?,?,?,TO_DATE(?),?,?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, rid);
			pstmt.setString(2, rtype);
			pstmt.setString(3, rname);
			pstmt.setString(4, addr);
			pstmt.setString(5, dob);
			// pstmt.setDate(5, (java.sql.Date) date);
			// pstmt.setString(4, addr);
			pstmt.setString(6, sex);
			pstmt.setLong(7, phone);
			pstmt.setString(8, email);
			int rs = pstmt.executeUpdate();
			// System.out.println("Reader Insertion Successful.");
			String query2 = "INSERT INTO REGISTER VALUES(?,?,SYSDATE)";
			pstmt = conn.prepareStatement(query2);
			pstmt.setInt(1, rid);
			pstmt.setInt(2, bid);
			int rs1 = pstmt.executeUpdate();
			if (rs != -1 && rs1 != -1) {
				System.out.println("Reader Insertion Successful.");
			} else {
				System.out.println("Reader Insertion Failed.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception");
			e.printStackTrace();
		}

	}

	private static void adminSearchDocument() {
		// TODO Auto-generated method stub
		try {

			System.out.println("How do you want to search the document?");
			System.out.println("1. Document Id");
			System.out.println("2. Title");
			System.out.println("3. Publisher Id");
			int rchoice = scan.nextInt();
			switch (rchoice) {
			case 1:
				System.out.println("Enter Document Id : ");
				int docId = scan.nextInt();

				String query = "SELECT DOCUMENTID,TITLE,PUBLISHERID FROM DOCUMENT WHERE DOCUMENTID = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, docId);
				ResultSet rs = pstmt.executeQuery();
				System.out.println("DOCUMENT ID\t\tTITLE\t\t\t\tPUBLISHER ID");
				System.out.println("-------------------------------------------------------------------");
				while (rs.next()) {
					int d = rs.getInt(1);
					String t = rs.getString(2);
					int p = rs.getInt(3);
					System.out.println(d + "\t\t" + t + "\t\t" + p);
				}
				System.out.println("--------------------------------------------------------------------");
				break;
			case 2:
				System.out.println("Enter Title : ");
				String title = scan.next();

				String query1 = "SELECT DOCUMENTID,TITLE,PUBLISHERID FROM DOCUMENT WHERE TITLE LIKE ?";
				pstmt = conn.prepareStatement(query1);
				pstmt.setString(1, title + "%");
				ResultSet rs1 = pstmt.executeQuery();
				System.out.println("DOCUMENT ID\t\tTITLE\t\t\t\tPUBLISHER ID");
				System.out.println("-------------------------------------------------------------------");
				while (rs1.next()) {
					int d = rs1.getInt(1);
					String t = rs1.getString(2);
					int p = rs1.getInt(3);
					System.out.println(d + "\t" + t + "\t" + p);
				}
				System.out.println("-------------------------------------------------------------------");
				break;
			case 3:
				System.out.println("Enter Publisher Id : ");
				int pubId = scan.nextInt();

				String query2 = "SELECT DOCUMENTID,TITLE,PUBLISHERID FROM DOCUMENT WHERE PUBLISHERID = ?";
				pstmt = conn.prepareStatement(query2);
				pstmt.setInt(1, pubId);
				ResultSet rs2 = pstmt.executeQuery();
				System.out.println("DOCUMENT ID\t\t\tTITLE\t\t\t\tPUBLISHER ID");
				System.out.println("-------------------------------------------------------------------");
				while (rs2.next()) {
					int d = rs2.getInt(1);
					String t = rs2.getString(2);
					int p = rs2.getInt(3);
					System.out.println(d + "\t" + t + "\t" + p);
				}
				System.out.println("---------------------------------------------------------------------");
				break;
			default:
				System.out.println("Bad Choice. Enter 1, 2 or 3. Press 'Y' to continue Admin Operations and search again");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void adminAddDocument() {
		// TODO Auto-generated method stub
		int docId = 0;
		try {
			String query = "SELECT MAX(DOCUMENTID) FROM DOCUMENT";
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				docId = rs.getInt(1);
			}
			docId++;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Please Select the type of Document : ");
		System.out.println("1. Book");
		System.out.println("2. Conference");
		System.out.println("3. Journal");
		int ch = scan.nextInt();
		switch (ch) {
		case 1:
			System.out.println("Please Enter book details.");
			addBook(docId);
			break;
		case 2:
			System.out.println("Please Enter Conference details.");
			addConference(docId);
			break;
		case 3:
			System.out.println("Please Enter Conference details.");
			addJournal(docId);
			break;
		default:
			System.out.println("Invalid Entry!");
			break;
		}
	}

	private static void addJournal(int docId) {
		// TODO Auto-generated method stub
		try {

			System.out.print("Enter Journal Title : ");
			String title = scan.next();
			System.out.println("\nEnter Publisher Id : ");
			int pubId = scan.nextInt();
			System.out.println("\nEnter Publishing date (DD-MMM-YYYY): ");
			String pubdate = scan.next();
			System.out.println("\nEnter Journal Volume Number: ");
			int journalVol = scan.nextInt();
			System.out.println("\nEnter Journal Issue Number: ");
			int journalIssue = scan.nextInt();
			System.out.println("\nEnter Journal Scope: ");
			String journalScope = scan.next();
			System.out.println("\nEnter Conference Editor ID: ");
			int journalEditor = scan.nextInt();
			String query = "INSERT INTO DOCUMENT VALUES(?,?,TO_DATE(?),?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, docId);
			pstmt.setString(2, title);
			pstmt.setString(3, pubdate);
			pstmt.setInt(4, pubId);
			int rs = pstmt.executeUpdate();
			String query1 = "INSERT INTO JOURNAL_VOLUME VALUES(?,?,?,?,?)";
			pstmt = conn.prepareStatement(query1);
			pstmt.setInt(1, docId);
			pstmt.setInt(2, journalVol);
			pstmt.setInt(3, journalIssue);
			pstmt.setString(4, journalScope);
			pstmt.setInt(5, journalEditor);
			int rs1 = pstmt.executeUpdate();
			if (rs != -1 && rs1 != -1) {
				System.out.println("Journal Inserted Successfully.");
			} else {
				System.out.println("Journal Insertion Failed.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void addConference(int docId) {
		// TODO Auto-generated method stub
		try {

			System.out.print("Enter Conference Title : ");
			String title = scan.next();
			System.out.println("\nEnter Publisher Id : ");
			int pubId = scan.nextInt();
			System.out.println("\nEnter Publishing date (DD-MMM-YYYY): ");
			String pubdate = scan.next();
			System.out.println("\nEnter Conference Date (DD-MMM-YYYY): ");
			String confDate = scan.next();
			System.out.println("\nEnter Conference location: ");
			String confLocation = scan.next();
			System.out.println("\nEnter Conference Editor ID: ");
			int Confeditor = scan.nextInt();
			String query = "INSERT INTO DOCUMENT VALUES(?,?,TO_DATE(?),?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, docId);
			pstmt.setString(2, title);
			pstmt.setString(3, pubdate);
			pstmt.setInt(4, pubId);

			int rs = pstmt.executeUpdate();
			String query1 = "INSERT INTO CONFERENCE VALUES(?,TO_DATE(?),?,?)";
			pstmt = conn.prepareStatement(query1);
			pstmt.setInt(1, docId);
			pstmt.setString(2, confDate);
			pstmt.setString(3, confLocation);
			pstmt.setInt(4, Confeditor);
			int rs1 = pstmt.executeUpdate();
			if (rs != -1 && rs1 != -1) {
				System.out.println("Conference Inserted Successfully.");
			} else {
				System.out.println("Conference Insertion Failed.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void addBook(int docId) {

		try {

			System.out.print("Enter Document Title : ");
			String title = scan.next();
			System.out.println("\nEnter Publisher Id : ");
			int pubId = scan.nextInt();
			System.out.println("\nEnter Publishing date (DD-MMM-YYYY): ");
			String pubdate = scan.next();
			System.out.println("\nEnter ISBN #: ");
			String bookISBN = scan.next();
			System.out.println("\nEnter EDITION: ");
			int bookedition = scan.nextInt();
			System.out.println("\nEnter Author Id: ");
			int authid = scan.nextInt();
			System.out.println("\nEnter Branch Id: ");
			int brid = scan.nextInt();

			String query = "INSERT INTO DOCUMENT VALUES(?,?,TO_DATE(?),?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, docId);
			pstmt.setString(2, title);
			pstmt.setInt(4, pubId);
			pstmt.setString(3, pubdate);
			int rs = pstmt.executeUpdate();

			String query1 = "INSERT INTO BOOK VALUES(?,?,?,?)";
			pstmt = conn.prepareStatement(query1);
			pstmt.setInt(1, docId);
			pstmt.setString(2, bookISBN);
			pstmt.setInt(3, bookedition);
			pstmt.setInt(4, authid);
			int rs1 = pstmt.executeUpdate();

			String query3 = "INSERT INTO DOCUMENTCOPY VALUES(?,1,?,0)";
			pstmt = conn.prepareStatement(query3);
			pstmt.setInt(1, docId);
			pstmt.setInt(2, brid);
			int rs2 = pstmt.executeUpdate();

			if (rs != -1 && rs1 != -1 && rs2 != -1) {
				System.out.println("Document Insertion Successful.");
			} else {
				System.out.println("Document Insertion Failed.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub

	}

}
