package server.commands;

import common.exceptions.IncorrectCommandInputException;
import common.interaction.User;
import server.utilities.CollectionManager;
import server.utilities.CommandManager;
import server.utilities.ResponseOutputer;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Concrete command, History of executed server.commands
 */
public class HistoryCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public HistoryCommand(CollectionManager collectionManager) {
        super("history", "history : вывести последние 11 команд (без их аргументов)");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] arg, User user, ReentrantLock reentrantLock) throws IOException {
        reentrantLock.lock();
        try {
            if (!(arg.length == 1)) throw new IncorrectCommandInputException();
            CommandManager.addToHistory(getName());
            collectionManager.history();
        } catch (IncorrectCommandInputException err) {
            ResponseOutputer.append("Использование: " + getDescription());
        }finally {
            reentrantLock.unlock();
        }
    }
}
