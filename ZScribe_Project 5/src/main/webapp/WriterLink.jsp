	<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>ZScribe</title>
	</head>
	<style>
		body {
		/*	background-image:url("wave.png");*/
			background-size:cover;
			margin-top: 20vh;
		    font-family: 'Nunito', sans-serif;
		}
		img {
			width: 250px;
			padding: 20px;
		}
		.wrapper {flex-direction: column;
			padding-top: 30vh;
			display: flex;
			justify-content: center;
			align-items: center;
			padding: 5px;
			margin-top:-140px;
		}
		.block {
			flex-direction: row;
			text-align: center;
			font-weight: bold;
			background: #FFF0E6;
			filter: drop-shadow(7px 4px 4px rgba(0,0,0,0.2));
			border-radius: 20px;
			width: 300px;
			margin: 50px;
			padding: 20px;
			font-family: sans-serif;
		}
		a {
			text-decoration: none;
			color: white;
		}
		.back button {
			width: 250px;
			padding: 20px 40px;
			color: white;
			font-size: 25px;
		    background-color: #F0483E;
		    border: 0px;
		    border-radius: 10px;
			margin: auto;
			display: flex;
			justify-content: center;
			align-items: center;
		}
		.back{
			margin-top:30px;
		}
	</style>
	<body>
		<div class="wrapper">
			<video width="400px" autoplay>
				<source src="tick.mp4">
			</video>
			<div style="display: flex;flex-direction: row;">
				<div class="writer block">
					<a target=_blank style="color: #397AB8;" href=<%
					HttpSession sess = request.getSession();
					String wLink = (String)sess.getAttribute("writerLink");
					out.println(wLink);
					%>>
					<div class="writer-pic content">
						<img style="width: 100px;" class="writer-logo logo" src="Zoho_Writer.png">
					</div>
					<div class="writer-link content">
						<h2 class="heading">Click to view [Writer]</h2>
					</div>
					</a>
				</div>
			</div>
		</div>
		<div class="back">
			<button class='back-btn'><a href='Option.html'>Create new</a></button>
		</div>
	</body>
	</html>