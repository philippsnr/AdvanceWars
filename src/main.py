import curses, re, sys, json, hashlib, os

def startScreen(stdscr, height, width):
    curses.curs_set(0)
    stdscr.clear()
    pair_number = [1, 2]
    ky = 0
    text1 = "Anmelden"
    text2 = "Neuen Account anlegen"
    y = height // 2
    x = width
    #exitText(stdscr, height, width)
    go = True
    while go == True:
        stdscr.addstr(y, (x - len(text1)) // 2, text1, curses.color_pair(pair_number[0]) | curses.A_BOLD)
        stdscr.addstr(y + 5, (x - len(text2)) // 2, text2, curses.color_pair(pair_number[1]) | curses.A_BOLD)
        stdscr.refresh()
        ky, pair_number, go = choiceFunction(stdscr, ky, pair_number, go)
    return ky

def choiceFunction(stdscr, ky, pair_number, go):
    ky_max = len(pair_number) - 1
    ky_min = 0
    key = stdscr.getch()
    if key == curses.KEY_DOWN and ky < ky_max: #ky -> key_y
        pair_number[ky] = 2
        pair_number[ky + 1] = 1
        ky += 1
    elif key == curses.KEY_UP and ky > ky_min:
        pair_number[ky - 1] = 1
        pair_number[ky] = 2
        ky -= 1
    elif key == 27:
        isShureToExitTheProgram(stdscr)
    elif key in [10, 13]: #Entertaste
        go = False
    #elif key == ord('q'):
        #break
    return ky, pair_number, go

def register(stdscr, height, width):
    stdscr.clear()
    pair_number = [1, 2, 2]
    ky = 0
    go = True
    text1 = "Neuen Account anlegen:"
    text2 = "E-Mail-Adresse:"
    text3 = "Master-Passwort:"
    text4 = "Master-Passwort nochmal eingeben:"
    y = height // 2
    x = width // 2
    stdscr.addstr(y - 8, x - (len(text1) // 2), text1, curses.color_pair(2) | curses.A_BOLD)
    go2 = True
    password1_available = False
    password2_available = False
    mail_correct = False
    password1_correct = False
    password2_correct = False
    exitText(stdscr, height, width)
    while go2 == True:
        while go == True:
            stdscr.addstr(y - 4, x - len(text2) - 8, text2, curses.color_pair(pair_number[0]) | curses.A_BOLD)
            stdscr.addstr(y, x - len(text2) - 8, text3, curses.color_pair(pair_number[1]) | curses.A_BOLD)
            stdscr.addstr(y + 4, x - len(text4) - 8, text4, curses.color_pair(pair_number[2]) | curses.A_BOLD)
            stdscr.refresh()
            ky, pair_number, go = choiceFunction(stdscr, ky, pair_number, go)
        if ky == 0:
            stdscr.move(y - 4, x - 6)
            input_y, input_x = y - 4, x - 6
            isPassword = False
        elif ky == 1:
            stdscr.move(y, x - 6)
            input_y, input_x = y, x - 6
            isPassword = True
        elif ky == 2:
            stdscr.move(y + 4, x - 6)
            input_y, input_x = y + 4, x - 6
            isPassword = True
        curses.curs_set(1)
        user_input = inputFunction(stdscr, input_y, input_x, isPassword)
        curses.curs_set(0)
        if ky == 0:
            mail = user_input
            if isMailCorrect(mail) == True:
                mail_correct = True
                stdscr.addstr(y - 4, x - 6 + len(mail) + 2, '\u2713')
                stdscr.addstr(y - 3, x - 6, ' ' * len("Eingabe nicht korrekt!"))
            else:
                mail_correct = False
                stdscr.addstr(y - 3, x - 6, "Eingabe nicht korrekt!", curses.color_pair(3))
                stdscr.addstr(y - 4, x - 6 + len(mail), ' ' * (width - x - 6 + len(mail)))
        elif ky == 1:
            password = user_input
            password1_available = True
        elif ky == 2:
            password2 = user_input
            password2_available = True
        if password1_available == True:
            word = password
            if False == isPasswordCorrect(word):
                stdscr.addstr(y + 1, x, "Passwort muss 8 Zeichen lang, Klein-, Großbuchstaben, Zahlen, Sonderzeichen beinhalten", curses.color_pair(3))
            else:
                password1_correct = True
        if password2_available == True:
            word = password2
            if isPasswordCorrect(word) == False:
                stdscr.addstr(y + 5, x, "Passwort muss 8 Zeichen lang, Klein-, Großbuchstaben, Zahlen, Sonderzeichen beinhalten", curses.color_pair(3))
            else:
                password2_correct = True
        if mail_correct == True and password1_correct == True and password2_correct == True:
            go2 = False
        else:
            go = True
        stdscr.refresh()
    safeRegisterData(mail, password)
    stdscr.getch()

def safeRegisterData(mail, password):
    hashed_password = hashPassword(password)
    new_data = {
        mail: {
            "mail": mail,
            "master-password": hashed_password,
            "passwords-list": [],
            "passwords": { 
            }
        }
    }
    with open('./data.json', 'r') as json_file:
        data = json.load(json_file)
    data["accounts"].update(new_data)
    with open('./data.json', 'w') as json_file:
        json.dump(data, json_file, indent = 4)

def readDataJson():
    with open('./data.json', 'r', encoding = 'utf-8') as json_file:
        data = json.load(json_file)
    return data

def signIn(stdscr, height, width):
    stdscr.clear()
    data = readDataJson()
    text1 = "Anmelden:"
    text2 = "E-Mail-Adresse:"
    text3 = "Master-Passwort:"
    y = height // 2
    x = width // 2
    pair_number = [1, 2]
    go, go2 = True, True
    ky = 0
    mail_correct, password_correct = False, False
    stdscr.addstr(y - 8, x - len(text1), text1, curses.color_pair(2) | curses.A_BOLD)
    exitText(stdscr, height, width)
    stdscr.refresh()
    while go2 == True:
        while go == True:
            stdscr.addstr(y - 4, x - len(text2) - 8, text2, curses.color_pair(pair_number[0]) | curses.A_BOLD)
            stdscr.addstr(y, x - len(text3) - 8, text3, curses.color_pair(pair_number[1]) | curses.A_BOLD)
            stdscr.refresh()
            ky, pair_number, go = choiceFunction(stdscr, ky, pair_number, go)
        if ky == 0:
            stdscr.move(y - 4, x - 6)
            input_y, input_x = y - 4, x - 6
            isPassword = False
        elif ky == 1:
            stdscr.move(y, x - 6)
            input_y, input_x = y, x - 6
            isPassword = True
        curses.curs_set(1)
        user_input = inputFunction(stdscr, input_y, input_x, isPassword)
        curses.curs_set(0)
        if ky == 0:
            mail = user_input
            if mail == data['accounts'][mail]["mail"]:
                mail_correct = True
            else:
                mail_correct = False
        if ky == 1:
            password = user_input
            hashed_password = hashPassword(password)
            if hashed_password == data['accounts'][mail]['master-password']:
                password_correct = True
            else:
                password_correct = False
        if mail_correct == True and password_correct == True:
            go2 = False
            return mail
        else:
            go = True
        stdscr.refresh()

def passwordManager(stdscr, height, width, mail):
    stdscr.clear()
    y = height // 2
    x = width // 2
    ky = 0
    go = True
    data = readDataJson()
    passwords_list = data["accounts"][mail]["passwords-list"]
    pair_number = [1, 2]
    text1 = "Passwörter:"
    text2 = "neues Passwort hinzufügen"
    text3 = "Passwort anzeigen:"
    stdscr.addstr(y - 10, x - (len(text1) //2), text1, curses.color_pair(2) | curses.A_BOLD | curses.A_UNDERLINE)
    a = -4
    b = 1
    x_new = 5
    for passwords in passwords_list:
        stdscr.addstr(y + a, x_new, data["accounts"][mail]["passwords"][passwords]["name"], curses.color_pair(2) | curses.A_BOLD)
        a += 2
        b += 1
        if a == 20:
            x_new = 20
        stdscr.refresh()
    line = "-" * width
    stdscr.addstr(y - 5, 0, line)   
    while go == True:
        stdscr.addstr(y - 8, x - len(text3) - 10, text3, curses.color_pair(pair_number[0]) | curses.A_BOLD)
        stdscr.addstr(y - 6, x - len(text2) - 10, text2, curses.color_pair(pair_number[1]) | curses.A_BOLD)
        ky, pair_number, go = choiceFunction(stdscr, ky, pair_number, go)
        stdscr.refresh()   
    if ky == 0:
        isPassword = False
        input_y, input_x = y - 8, x - 8
        stdscr.move(input_y, input_x)
        curses.curs_set(1)
        data_to_be_shown = inputFunction(stdscr, input_y, input_x, isPassword)
        curses.curs_set(0)
        try:
            go = False
            showPassword(stdscr, data, mail, data_to_be_shown, y, x, height, width)
        except KeyError:
            passwordManager(stdscr, height, width, mail)
    else:
        addNewPassword(stdscr, data, mail, height, width, y, x)

def addNewPassword(stdscr, data, mail, height, width, y, x):
    stdscr.clear()
    text1 = "Name:"
    text2 = "URL:"
    text3 = "Notiz:"
    text4 = "Passwort:"
    text5 = "Neues Passwort anlegen:"
    pair_number = [1, 2, 2, 2, 2, 2]
    go, go2 = True, True
    ky = 0
    url, notes = "", ""
    name_available, password_available = False, False
    stdscr.addstr(y - 10, x - (len(text5) //2), text5, curses.color_pair(2) | curses.A_BOLD)
    stdscr.refresh()
    while go2 == True:
        while go == True:
            stdscr.addstr(y - 6, x - 20, text1, curses.color_pair(pair_number[0]) | curses.A_BOLD)
            stdscr.addstr(y - 4, x - 20, text2, curses.color_pair(pair_number[1]) | curses.A_BOLD)
            stdscr.addstr(y - 2, x - 20, text3, curses.color_pair(pair_number[2]) | curses.A_BOLD)
            stdscr.addstr(y, x - 20, text4, curses.color_pair(pair_number[3]) | curses.A_BOLD)
            stdscr.addstr(y + 4, x - 20, "Speichern", curses.color_pair(pair_number[4]) | curses.A_BOLD)
            stdscr.addstr(y + 10, x - 20, "Zurück", curses.color_pair(pair_number[5]) | curses.A_BOLD)               
            stdscr.refresh()        
            ky, pair_number, go = choiceFunction(stdscr, ky, pair_number, go)
        if ky == 5:
            passwordManager(stdscr, height, width, mail)
        elif ky == 4 and name_available == True and password_available == True:
            go2 = False
            new_data= {
                name: {
                    "name": name,
                    "password": password,
                    "url": url,
                    "text": notes,
                    "oldpasswordlist": []
                }
            }
            new_data[name]["oldpasswordlist"].append(password)
            safeNewPasswordData(new_data, mail, name)
            passwordManager(stdscr, height, width, mail)
        else:
            go = True
            if ky == 0:
                input_y, input_x = y - 6, x - 18 + len(text1)
                stdscr.move(input_y, input_x)
                isPassword = False
            elif ky == 1:
                input_y, input_x = y - 4, x - 18 + len(text2)
                stdscr.move(input_y, input_x)
                isPassword = False
            elif ky == 2:
                input_y, input_x = y - 2, x - 18 + len(text3)
                stdscr.move(input_y, input_x)
                isPassword = False
            elif ky == 3:
                input_y, input_x = y, x - 18 + len(text4)
                stdscr.move(input_y, input_x)
                isPassword = True
            curses.curs_set(1)
            user_input = inputFunction(stdscr, input_y, input_x, isPassword)
            curses.curs_set(0)
            if ky == 0:
                name = user_input
                name_available = True
            elif ky == 1:
                url = user_input
            elif ky == 2:
                notes = user_input
            elif ky == 3:
                password = user_input
                if True == isPasswordCorrect(password):
                    password_available = True

def safeNewPasswordData(new_data, mail, name):
    with open('./data.json', 'r') as json_file:
        data = json.load(json_file)
    data["accounts"][mail]["passwords-list"].append(name)
    data["accounts"][mail]["passwords"].update(new_data)
    with open('./data.json', 'w') as json_file:
        json.dump(data, json_file, indent = 4)

def showPassword(stdscr, data, mail, data_to_be_shown, y, x, height, width):
    stdscr.clear()
    pair_number = [1, 2, 2, 2]
    go, go2 = True, True
    ky = 0
    name = data['accounts'][mail]['passwords'][data_to_be_shown]['name']
    url = data['accounts'][mail]['passwords'][data_to_be_shown]['url']
    notes = data['accounts'][mail]['passwords'][data_to_be_shown]['text']
    password = data["accounts"][mail]["passwords"][data_to_be_shown]["password"]
    stdscr.addstr(y - 8, x - 10, f"Name: {name}", curses.color_pair(2) | curses.A_BOLD)
    stdscr.addstr(y - 2, x - 10, f"Link: {url}", curses.color_pair(2) | curses.A_BOLD)
    stdscr.addstr(y, x - 10, f"Notiz: {notes}", curses.color_pair(2) | curses.A_BOLD)
    stdscr.refresh()
    while go2 == True:
        while go == True:
            stdscr.addstr(y - 6, x - 10, "Passwort anzeigen:", curses.color_pair(pair_number[0]) | curses.A_BOLD)
            stdscr.addstr(y - 4, x - 10, "Passwort kopieren", curses.color_pair(pair_number[1]) | curses.A_BOLD)
            stdscr.addstr(y + 4, x - 10, "Enträge ändern", curses.color_pair(pair_number[2]) | curses.A_BOLD)
            stdscr.addstr(y + 8, x - 10, "Zurück", curses.color_pair(pair_number[3]) | curses.A_BOLD)
            ky, pair_number, go = choiceFunction(stdscr, ky, pair_number, go)
        if ky == 0:
            stdscr.addstr(y - 6, x - 10 + len("Passwort anzeigen") + 2, password)
            stdscr.refresh()
        if ky == 1:
            stdscr.addstr(y - 4, x - 10 + len("Passwort kopieren") + 2, "Passwort in Zwischenablage kopiert")
        elif ky == 2:
            go2 = False
            changeData(stdscr, height, width, mail, name, url, notes, password, data_to_be_shown, data)
        elif ky == 3:
            passwordManager(stdscr, height, width, mail)
        go = True
    stdscr.getch()

def changeData(stdscr, height, width, mail, name, url, notes, password, data_to_be_shown, data):
    stdscr.clear()
    x = width //2
    y = height //2
    text1 = "Einträge ändern:"
    go, go2 = True, True
    ky = 0
    pair_number = [1, 2, 2, 2, 2, 2]
    stdscr.addstr(y - 10, x - len(text1), text1, curses.color_pair(2) | curses.A_BOLD)
    stdscr.addstr(y - 6, x - 30, f"Alter Name: {name}", curses.color_pair(2) | curses.A_BOLD)
    stdscr.addstr(y - 2, x - 30, f"Alter Link {url}", curses.color_pair(2) | curses.A_BOLD)
    stdscr.addstr(y + 2, x - 30, f"Alte Notiz: {notes}", curses.color_pair(2) | curses.A_BOLD)
    stdscr.addstr(y + 6, x - 30, f"Altes Passwort: {password}", curses.color_pair(2) | curses.A_BOLD)
    stdscr.refresh()
    while go2 == True:
        while go == True:
            stdscr.addstr(y - 5, x - 30, "Neuer Name:", curses.color_pair(pair_number[0]) | curses.A_BOLD)
            stdscr.addstr(y - 1, x - 30, "Neuer Link:", curses.color_pair(pair_number[1]) | curses.A_BOLD)
            stdscr.addstr(y + 3, x - 30, "Neue Notiz:", curses.color_pair(pair_number[2]) | curses.A_BOLD)
            stdscr.addstr(y + 7, x - 30, "Neues Passwort:", curses.color_pair(pair_number[3]) | curses.A_BOLD)
            stdscr.addstr(y + 10, x - 30, "Speichern", curses.color_pair(pair_number[4]) | curses.A_BOLD)
            stdscr.addstr(y + 12, x - 30, "Zurück", curses.color_pair(pair_number[5]) | curses.A_BOLD)
            stdscr.refresh()
            ky, pair_number, go = choiceFunction(stdscr, ky, pair_number, go)
        if ky == 5:
            go2 = False
            showPassword(stdscr, data, mail, data_to_be_shown, y, x, height, width)
        elif ky == 4:
            go2 = False
            new_data = {
                name: {
                    "name": name,
                    "password": password,
                    "url": url,
                    "text": notes,
                    "oldpasswordlist": []
                }
            }
        else:
            go = True
            if ky == 0:
                input_y, input_x = y - 5, x - 28 + len("Neuer Name:")
                stdscr.move(input_y, input_x)
                isPassword = False
            elif ky == 1:
                input_y, input_x = y - 1, x - 28 + len("Neuer Link:")
                stdscr.move(input_y, input_x)
                isPassword = False
            elif ky == 2:
                input_y, input_x = y + 3, x - 28 + len("Neue Notiz:")
                stdscr.move(input_y, input_x)
                isPassword = False
            elif ky == 3:
                input_y, input_x = y + 7, x - 28 + len("Neues Passwort:")
                stdscr.addstr(y + 8, x - 30, " " * width)
                stdscr.move(input_y, input_x)
                isPassword = True
            curses.curs_set(1)
            user_input = inputFunction(stdscr, input_y, input_x, isPassword)
            curses.curs_set(0)
            if ky == 0:
                new_name = user_input
                if new_name != name:
                    name = new_name
            elif ky == 1:
                new_url = user_input
                if new_url != url:
                    url = new_url
            elif ky == 2:
                new_notes = user_input
                if new_notes != notes:
                    notes = new_notes
            elif ky == 3:
                new_password = user_input
                if new_password in data["accounts"][mail]["passwords"][name]["oldpasswordlist"]:
                    stdscr.addstr(y + 8, x - 30, "Passwort schon mal verwendet", curses.color_pair(3))
                    stdscr.refresh()
                elif True == isPasswordCorrect(new_password):
                    password = new_password
                else:
                    stdscr.addstr(y + 8, x - 30, "Passwort unsicher", curses.color_pair(3))
                    stdscr.refresh()
                    
    stdscr.getch()

    
    

def exitText(stdscr, height, width):
    pass
    #stdscr.addstr(height - 1, 0, ' ' * width)
    #stdscr.refresh()
    #stdscr.addstr(height - 1, 2, "Drücke \"Esc\" zum beenden", curses.color_pair(2))
    #stdscr.refresh()

def hashPassword(password):
    sha_signature = hashlib.sha256(password.encode()).hexdigest()
    return sha_signature

def isPasswordCorrect(word):
    if len(word) < 8:
        return False
    if not re.search(r'\d', word):
        return False
    if not re.search(r'[A-Z]', word):
        return False
    if not re.search(r'[a-z]', word):
        return False
    if not re.search(r'[_!@#$%^&*(),.?":{}|<>-]', word):
        return False
    return True
    
def isMailCorrect(mail):
    pattern = re.compile(r'\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}\b')
    return bool(pattern.match(mail))

def inputFunction(stdscr, input_y, input_x, isPassword):
    beginx = input_x
    user_input = ""
    go = True
    while go == True:
        inp = stdscr.getch()
        if inp in [10, 13]:
            go = False
        elif inp == curses.KEY_BACKSPACE:
            if input_x > beginx:
                stdscr.addch(input_y, input_x - 1, ' ')
                stdscr.move(input_y, input_x - 1)
                stdscr.refresh()
                user_input = user_input[:-1]
                input_x -= 1
        elif inp == curses.KEY_RIGHT:
            if input_x < beginx + len(user_input):
                input_x += 1
                stdscr.move(input_y, input_x)
                stdscr.refresh()
        elif inp == curses.KEY_LEFT:
            if input_x > beginx:
                input_x -= 1
                stdscr.move(input_y, input_x)
                stdscr.refresh()
        elif inp == curses.KEY_UP or inp == curses.KEY_DOWN:
            pass
        elif inp == 27:
            isShureToExitTheProgram(stdscr)
        else:
            if isPassword == False:
                stdscr.addch(input_y, input_x, chr(inp))
                stdscr.refresh()
                user_input += chr(inp)
                input_x += 1
            elif isPassword == True:
                stdscr.addch(input_y, input_x, '*')
                stdscr.refresh()
                user_input += chr(inp)
                input_x += 1
    return user_input

def isShureToExitTheProgram(stdscr):
    height, width = stdscr.getmaxyx()
    text = "Sicher, dass Sie das Programm beenden wollen? \"Enter\":beenden | \"Esc\":abbrechen"
    stdscr.addstr(height - 1, 2, ' ' * len("Drücke \"Esc\" zum beenden"))
    stdscr.refresh()
    stdscr.addstr(height - 1, (width - len(text)) // 2, text, curses.color_pair(2))
    stdscr.refresh()
    exit_input = stdscr.getch()
    if exit_input == [10, 13]:
        sys.exit(0)
    elif exit_input == 27:
        exitText(stdscr, width, height)

def createAccountsFile():
    if os.path.exists('./data.json'):
        pass
    else:
        data = {
            "accounts": {
            }
        }
        with open('./data.json', 'w') as file:
            json.dump(data, file, ensure_ascii = False, indent = 4)

def main(stdscr):
    #curses.resize_term(30, 50)
    begin = True
    SELF_GREY = 1
    curses.start_color()
    curses.init_color(SELF_GREY, 400, 400, 400)
    curses.init_pair(1, curses.COLOR_GREEN, SELF_GREY) #Schriftfarbe: Grün, Hintergrundfarbe: Hellgrau
    curses.init_pair(2, curses.COLOR_GREEN, curses.COLOR_BLACK) #Schriftfarbe: Grün, Hintergrundfarbe: Schwarz
    curses.init_pair(3, curses.COLOR_RED, curses.COLOR_BLACK) #Schriftfarbe: Rot, Hintergrundfarbe: Schwarz
    height, width = stdscr.getmaxyx()
    createAccountsFile()
    while begin == True:
        ky = startScreen(stdscr, height, width)
        if ky == 0:
            mail = signIn(stdscr, height, width)
            begin = False
        elif ky == 1:
            register(stdscr, height, width)
    passwordManager(stdscr, height, width, mail)

if __name__ == "__main__":
    curses.wrapper(main)