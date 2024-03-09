/*

 C++ przez OpenGL - szablon do æwiczeñ laboratoryjnych
 (C) Micha³ Turek.

*/

#ifdef _RYSOWANIE


	/******************* SZABLON **************************/
	

	// TEREN
	// Tekstura podloza jest zapisana w pliku "data/land.bmp", definiowana bezpoœrednio w 3ds. 
	// Wymagany format pliku: bmp, 24 bity na pixel.
	



	glPushMatrix();
		glTranslatef(0,0,0);
		rysujModel("teren99"); // malowanie podloza
		rysujModel("niebo"); // malowanie nieba
	glPopMatrix();
	
	




	// MODELE 3ds:
	// Modele 3ds znajdujace sie w katalogu /data s¹ autoamtycznie ladowane i rejestrowane pod nazwami zbieznymi z nazwami plikow
	// Aby narysowaæ model nalezy wywo³aæ funkcjê: rysujModel ("nazwa_modelu");
	// Nazwa_modelu mo¿e byæ podana literemi du¿ymi lub ma³ymi, z rozszerzeniem pliku lub bez.
	
	// przyklad:
	
	GLfloat  matSpecular1[4] = {1,0,0,1};
	GLfloat  matAmbient1[4] = {1,0,0,1};
	GLfloat  matDiffuse1[4] = {1,0,0,1};
	GLfloat  matEmission1[4] = {0,0,0,1};
	GLfloat  matShininess1 = 1;
		
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR,matSpecular1);
	glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT,matAmbient1);
	glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE,matDiffuse1);
	glMaterialfv(GL_FRONT_AND_BACK, GL_EMISSION,matEmission1);
	glMateriali(GL_FRONT_AND_BACK,GL_SHININESS,matShininess1);




	glPushMatrix();
	glTranslatef(0, 10, 0);
	rysujModel("kostki"); // malowanie podloza
	
	glPopMatrix();









	for (int a = 0; a<6; a++)
	{
		glPushMatrix();
		glTranslatef(drzewa_pozycje[a][0],-2,drzewa_pozycje[a][1]);
		glRotatef(drzewa_rotacje [a][0],0,1,0);
		glRotatef(drzewa_rotacje [a][1],1,0,0);
		rysujModel ("drzewo3");
		glPopMatrix();
	}





	/******************************************************/


#undef _RYSOWANIE
#endif
