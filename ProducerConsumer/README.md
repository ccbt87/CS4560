* The producer should open an input file and repeatedly copy values to a circular buffer.
* The consumer should open an output file and repeatedly copy values from the same circular buffer to the file.
* For both the producer and consumer, a random number of bytes between 1 and n, inclusive, should be copied each iteration, where n is specified by the user.  The number of bytes should be randomized each iteration.
