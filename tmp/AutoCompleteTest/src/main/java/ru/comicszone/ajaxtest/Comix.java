package ru.comicszone.ajaxtest;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Comix
{
    private String comixId;
	private String comixName;
	private String comixDescription;

	public String getComixId()
    {
		return comixId;
	}
	public void setComixId(String comixId)
    {
		this.comixId = comixId;
	}
	public String getComixName()
    {
		return comixName;
	}
	public void setComixName(String comixName)
    {
		this.comixName = comixName;
	}

    public String getComixDescription()
    {
        return comixDescription;
    }

    public void setComixDescription(String comixDescription)
    {
        this.comixDescription = comixDescription;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comix comix = (Comix) o;

        if (comixDescription != null ? !comixDescription.equals(comix.comixDescription) : comix.comixDescription != null)
            return false;
        if (comixId != null ? !comixId.equals(comix.comixId) : comix.comixId != null) return false;
        if (comixName != null ? !comixName.equals(comix.comixName) : comix.comixName != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = comixId != null ? comixId.hashCode() : 0;
        result = 31 * result + (comixName != null ? comixName.hashCode() : 0);
        result = 31 * result + (comixDescription != null ? comixDescription.hashCode() : 0);
        return result;
    }

}
