package sapper;

class Flag
{
    private Matrix flagMap;
    private int countOfColsedBoxes;

    void start ()
    {
        flagMap = new Matrix(Box.CLOSED);
        countOfColsedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    Box get (Coord coord)
    {
        return flagMap.get(coord);
    }

    void setOpenedToBox(Coord coord)
    {
        flagMap.set(coord, Box.OPENED);
        countOfColsedBoxes --;
    }

   private void setFlagedToBox(Coord coord)
    {
        flagMap.set(coord, Box.FLAGED);
    }

    void toggleFlagedToBox(Coord coord)
    {
        switch (flagMap.get(coord))
        {
            case FLAGED : setClosedToBox(coord);
            break;
            case CLOSED : setFlagedToBox(coord);
            break;
        }
    }

    void setClosedToBox(Coord coord)
    {
        flagMap.set(coord, Box.CLOSED);
    }

    int getCountOfClosedBoxes()
    {
        return countOfColsedBoxes;
    }

    void setBombedToBox(Coord coord)
    {
        flagMap.set(coord, Box.BOMBED);
    }

    void setOpenedToClosedBox(Coord coord)
    {
        if (flagMap.get(coord) == Box.CLOSED)
            flagMap.set(coord, Box.OPENED);
    }

    void setNobombToFlagedSafeBox(Coord coord)
    {
        if (flagMap.get(coord) == Box.FLAGED)
            flagMap.set(coord, Box.NOBOMB);
    }

    int getCountOfFlagedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordAround(coord))
            if (flagMap.get(around) == Box.FLAGED)
                count ++;
            return count;
    }
}
